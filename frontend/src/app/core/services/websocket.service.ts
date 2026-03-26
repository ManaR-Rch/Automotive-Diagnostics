import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private stompClient: Client | null = null;
  private newRdvSubject = new Subject<any>();
  private updateRdvSubject = new Subject<any>();
  private connectionAttempts = 0;
  private maxRetries = 5;
  
  public newRdv$ = this.newRdvSubject.asObservable();
  public updateRdv$ = this.updateRdvSubject.asObservable();

  constructor() {
    this.initializeIfBrowser();
  }

  private initializeIfBrowser(): void {
    if (typeof window === 'undefined') return; // SSR guard
    
    // Delay connection to allow page to be fully loaded
    setTimeout(() => this.connect(), 1000);
  }

  connect(): void {
    if (typeof window === 'undefined') return; // SSR guard
    if (this.stompClient?.active || this.stompClient?.connected) return;
    
    try {
      const url = '/ws/rendezvous';
      
      this.stompClient = new Client({
        connectHeaders: {},
        reconnectDelay: 5000,
        webSocketFactory: () => new SockJS(url),
        onConnect: () => this.onConnect(),
        onStompError: (frame) => this.onError(frame),
        onWebSocketError: (event) => this.onError(event)
      });

      this.stompClient.activate();
    } catch (error) {
      console.error('WebSocket initialization error:', error);
      this.reconnect();
    }
  }

  private onConnect(): void {
    console.log('✓ WebSocket connected');
    this.connectionAttempts = 0;
    
    if (!this.stompClient) return;
    
    this.stompClient.subscribe('/topic/rendezvous/new', (message: IMessage) => {
      try {
        const notification = JSON.parse(message.body);
        this.newRdvSubject.next(notification);
      } catch (e) {
        console.error('Error parsing notification:', e);
      }
    });

    this.stompClient.subscribe('/topic/rendezvous/update', (message: IMessage) => {
      try {
        const notification = JSON.parse(message.body);
        this.updateRdvSubject.next(notification);
      } catch (e) {
        console.error('Error parsing notification:', e);
      }
    });
  }

  private onError(error: any): void {
    console.error('WebSocket error:', error);
    this.reconnect();
  }

  private reconnect(): void {
    if (this.connectionAttempts < this.maxRetries) {
      this.connectionAttempts++;
      const delay = Math.min(1000 * Math.pow(2, this.connectionAttempts), 30000);
      console.log(`Retrying WebSocket connection in ${delay}ms...`);
      setTimeout(() => this.connect(), delay);
    }
  }

  disconnect(): void {
    if (this.stompClient && (this.stompClient.connected || this.stompClient.active)) {
      this.stompClient.deactivate().then(() => {
        console.log('WebSocket disconnected');
      });
    }
  }

  isConnected(): boolean {
    return this.stompClient ? this.stompClient.connected : false;
  }
}

