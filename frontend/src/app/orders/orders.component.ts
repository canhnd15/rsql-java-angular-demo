import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../services/order.service';
import { Order, OrderStatus, ApiResponse, PageResponse } from '../models/order.model';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './orders.component.html',
  styles: [`
    .filters {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 15px;
      margin-bottom: 20px;
      padding: 20px;
      background-color: #f8f9fa;
      border-radius: 4px;
    }
    
    .loading, .error {
      text-align: center;
      padding: 20px;
      font-size: 16px;
    }
    
    .error {
      color: #dc3545;
      background-color: #f8d7da;
      border: 1px solid #f5c6cb;
      border-radius: 4px;
    }
    
    .pagination {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 20px;
      padding: 15px 0;
    }
    
    .page-info {
      font-weight: 500;
      color: #666;
    }
    
    .btn:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }
  `]
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];
  loading = false;
  error: string | null = null;
  
  // Pagination
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;
  
  // Filters
  customerFilter = '';
  statusFilter = '';
  sortBy = '';

  constructor(private orderService: OrderService) {}

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    this.loading = true;
    this.error = null;

    let filters = '';
    if (this.customerFilter.trim()) {
      filters += `customerName=like="${this.customerFilter.trim()}"`;
    }
    if (this.statusFilter) {
      if (filters) filters += ';';
      filters += `status=="${this.statusFilter}"`;
    }

    this.orderService.getOrders(this.currentPage, this.pageSize, filters, this.sortBy, true)
      .subscribe({
        next: (response: ApiResponse<PageResponse<Order>>) => {
          this.orders = response.data.content;
          this.totalPages = response.data.totalPages;
          this.totalElements = response.data.totalElements;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load orders. Please try again.';
          this.loading = false;
          console.error('Error loading orders:', error);
        }
      });
  }

  applyFilters() {
    this.currentPage = 0;
    this.loadOrders();
  }

  applySorting() {
    this.currentPage = 0;
    this.loadOrders();
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.loadOrders();
  }
}

