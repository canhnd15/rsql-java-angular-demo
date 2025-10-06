import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderDetail, ApiResponse, PageResponse } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderDetailService {
  private apiUrl = '/api/order-details';

  constructor(private http: HttpClient) {}

  getOrderDetails(
    page: number = 0,
    size: number = 10,
    filters?: string,
    sorts?: string
  ): Observable<ApiResponse<PageResponse<OrderDetail>>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (filters) {
      params = params.set('filters', filters);
    }

    if (sorts) {
      params = params.set('sorts', sorts);
    }

    return this.http.get<ApiResponse<PageResponse<OrderDetail>>>(this.apiUrl, { params });
  }
}

