package com.pro.service;

import java.util.List;
import java.util.logging.Logger;

import com.pro.model.ProductCheckout;

import model.Promotion;

public class AdditionalItemOffer {
	Logger logger = LoggerFactory.getLogger(AdditionalItemOffer.class);
	private Promotion appliedPromotion;
	private ProductCheckout ProductCheckout;

	public AdditionalItemOffer() {
		appliedPromotion = new Promotion();
		ProductCheckout = new ProductCheckout();
	}

	public boolean CanExecute(ProductCheckout product, List<Promotion> promotions)
    {
        ProductCheckout = product;
        appliedPromotion = promotions.Where(x => x.ProductCode == product.ProductCode).FirstOrDefault();
        if (appliedPromotion != null && appliedPromotion.Type == PromotionTypeConstants.Single)
        {
            product.IsValidated = true;
            return true;
        }

        return false;
    }

	public double CalculateProductPrice(List<ProductCheckout> productCheckoutList) {
		double finalPrice = 0;
		try {
			int totalEligibleItems = ProductCheckout.Quantity / appliedPromotion.Quantity;
			int remainingItems = ProductCheckout.Quantity % appliedPromotion.Quantity;
			finalPrice = appliedPromotion.Price * totalEligibleItems + remainingItems * (ProductCheckout.DefaultPrice);

		} catch (ArithmeticException ex) {
			logger.log("Error in AdditionalItemOffer :" + ex.getLocalizedMessage());
		} catch (Exception e) {
			logger.log("Error in AdditionalItemOffer :" + e.getLocalizedMessage());
		}

		return finalPrice;
	}
}
