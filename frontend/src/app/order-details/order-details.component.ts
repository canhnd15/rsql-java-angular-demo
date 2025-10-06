import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderDetailService } from '../services/order-detail.service';
import { OrderDetail, ApiResponse, PageResponse } from '../models/order.model';

@Component({
  selector: 'app-order-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './order-details.component.html',
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
export class OrderDetailsComponent implements OnInit {
  orderDetails: OrderDetail[] = [];
  loading = false;
  error: string | null = null;
  
  // Pagination
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;
  
  // Filters
  productFilter = '';
  orderIdFilter = '';
  sortBy = '';

  constructor(private orderDetailService: OrderDetailService) {}

  ngOnInit() {
    this.loadOrderDetails();
  }

  loadOrderDetails() {
    this.loading = true;
    this.error = null;

    let filters = '';
    if (this.productFilter.trim()) {
      filters += `productName=like="${this.productFilter.trim()}"`;
    }
    if (this.orderIdFilter.trim()) {
      if (filters) filters += ';';
      filters += `orderId=="${this.orderIdFilter.trim()}"`;
    }

    this.orderDetailService.getOrderDetails(this.currentPage, this.pageSize, filters, this.sortBy)
      .subscribe({
        next: (response: ApiResponse<PageResponse<OrderDetail>>) => {
          this.orderDetails = response.data.content;
          this.totalPages = response.data.totalPages;
          this.totalElements = response.data.totalElements;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load order details. Please try again.';
          this.loading = false;
          console.error('Error loading order details:', error);
        }
      });
  }

  applyFilters() {
    this.currentPage = 0;
    this.loadOrderDetails();
  }

  applySorting() {
    this.currentPage = 0;
    this.loadOrderDetails();
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.loadOrderDetails();
  }
}

