import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <div class="container">
      <header>
        <h1>RSQL Order Management System</h1>
        <nav>
          <a routerLink="/orders" class="btn btn-primary">Orders</a>
          <a routerLink="/order-details" class="btn btn-secondary">Order Details</a>
        </nav>
      </header>
      <main>
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
      padding: 20px 0;
      border-bottom: 2px solid #e9ecef;
    }
    
    h1 {
      color: #333;
      font-size: 2rem;
    }
    
    nav {
      display: flex;
      gap: 10px;
    }
    
    nav a {
      text-decoration: none;
    }
  `]
})
export class AppComponent {
  title = 'rsql-frontend';
}

