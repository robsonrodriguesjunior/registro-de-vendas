import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { ITopSellingProductsView } from '../top-selling-products-view.model';
import { TopSellingProductsViewService } from '../service/top-selling-products-view.service';
import { TopSellingProductsViewFormService, TopSellingProductsViewFormGroup } from './top-selling-products-view-form.service';

@Component({
  standalone: true,
  selector: 'jhi-top-selling-products-view-update',
  templateUrl: './top-selling-products-view-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TopSellingProductsViewUpdateComponent implements OnInit {
  isSaving = false;
  topSellingProductsView: ITopSellingProductsView | null = null;

  productsCollection: IProduct[] = [];

  editForm: TopSellingProductsViewFormGroup = this.topSellingProductsViewFormService.createTopSellingProductsViewFormGroup();

  constructor(
    protected topSellingProductsViewService: TopSellingProductsViewService,
    protected topSellingProductsViewFormService: TopSellingProductsViewFormService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topSellingProductsView }) => {
      this.topSellingProductsView = topSellingProductsView;
      if (topSellingProductsView) {
        this.updateForm(topSellingProductsView);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const topSellingProductsView = this.topSellingProductsViewFormService.getTopSellingProductsView(this.editForm);
    if (topSellingProductsView.id !== null) {
      this.subscribeToSaveResponse(this.topSellingProductsViewService.update(topSellingProductsView));
    } else {
      this.subscribeToSaveResponse(this.topSellingProductsViewService.create(topSellingProductsView));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopSellingProductsView>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(topSellingProductsView: ITopSellingProductsView): void {
    this.topSellingProductsView = topSellingProductsView;
    this.topSellingProductsViewFormService.resetForm(this.editForm, topSellingProductsView);

    this.productsCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsCollection,
      topSellingProductsView.product,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query({ 'topSellingProductsViewId.specified': 'false' })
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing<IProduct>(products, this.topSellingProductsView?.product),
        ),
      )
      .subscribe((products: IProduct[]) => (this.productsCollection = products));
  }
}
