import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [RouterModule],
  template: `
    <footer class="bg-dark-800 border-t border-dark-600 mt-auto">
      <div class="max-w-7xl mx-auto px-6 py-10">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div class="col-span-1 md:col-span-2">
            <div class="flex items-center gap-3 mb-4">
              <div class="w-9 h-9 bg-primary-600 rounded-lg flex items-center justify-center">
                <svg class="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
                </svg>
              </div>
              <span class="text-white font-bold text-lg">MOTO<span class="text-primary-500">DIAG</span></span>
            </div>
            <p class="text-gray-500 text-sm leading-relaxed">
              Votre partenaire de confiance pour le diagnostic et la réparation automobile. 
              Excellence, rapidité et transparence à votre service.
            </p>
            <div class="flex gap-3 mt-4">
              <div class="w-8 h-8 bg-dark-600 hover:bg-primary-700 rounded-lg flex items-center justify-center cursor-pointer transition-colors">
                <svg class="w-4 h-4 text-gray-400" fill="currentColor" viewBox="0 0 24 24"><path d="M24 4.557c-.883.392-1.832.656-2.828.775 1.017-.609 1.798-1.574 2.165-2.724-.951.564-2.005.974-3.127 1.195-.897-.957-2.178-1.555-3.594-1.555-3.179 0-5.515 2.966-4.797 6.045-4.091-.205-7.719-2.165-10.148-5.144-1.29 2.213-.669 5.108 1.523 6.574-.806-.026-1.566-.247-2.229-.616-.054 2.281 1.581 4.415 3.949 4.89-.693.188-1.452.232-2.224.084.626 1.956 2.444 3.379 4.6 3.419-2.07 1.623-4.678 2.348-7.29 2.04 2.179 1.397 4.768 2.212 7.548 2.212 9.142 0 14.307-7.721 13.995-14.646.962-.695 1.797-1.562 2.457-2.549z"/></svg>
              </div>
              <div class="w-8 h-8 bg-dark-600 hover:bg-primary-700 rounded-lg flex items-center justify-center cursor-pointer transition-colors">
                <svg class="w-4 h-4 text-gray-400" fill="currentColor" viewBox="0 0 24 24"><path d="M9 8h-3v4h3v12h5v-12h3.642l.358-4h-4v-1.667c0-.955.192-1.333 1.115-1.333h2.885v-5h-3.808c-3.596 0-5.192 1.583-5.192 4.615v3.385z"/></svg>
              </div>
            </div>
          </div>
          <div>
            <h4 class="text-white font-semibold mb-3 text-sm">Services</h4>
            <ul class="space-y-2 text-sm text-gray-500">
              <li><a routerLink="/services" class="hover:text-primary-400 transition-colors">Diagnostic</a></li>
              <li><a routerLink="/services" class="hover:text-primary-400 transition-colors">Réparation</a></li>
              <li><a routerLink="/services" class="hover:text-primary-400 transition-colors">Entretien</a></li>
              <li><a routerLink="/services" class="hover:text-primary-400 transition-colors">Carrosserie</a></li>
            </ul>
          </div>
          <div>
            <h4 class="text-white font-semibold mb-3 text-sm">Contact</h4>
            <ul class="space-y-2 text-sm text-gray-500">
              <li class="flex items-center gap-2">
                <svg class="w-3.5 h-3.5 text-primary-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"/></svg>
                +123-456-7890
              </li>
              <li class="flex items-center gap-2">
                <svg class="w-3.5 h-3.5 text-primary-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/></svg>
                contact&#64;motodiag.com
              </li>
              <li>Lun-Sam: 8h–18h</li>
            </ul>
          </div>
        </div>
        <div class="border-t border-dark-600 mt-8 pt-6 flex flex-col sm:flex-row justify-between items-center gap-4">
          <p class="text-xs text-gray-600">&copy; 2025 MotoDiag. Tous droits réservés.</p>
          <p class="text-xs text-gray-600">Diagnostic Automobile Professionnel</p>
        </div>
      </div>
    </footer>
  `
})
export class FooterComponent {}
