package com.pro.service;

import java.util.List;

import com.pro.businesslayer.Logger;
import com.pro.model.ProductCheckout;

import model.Promotion;

public class ComboOffer {
	
	Logger logger = LoggerFactory.getLogger(ComboOffer.class);
	 Promotion appliedPromotion;
     ProductCheckout recentProductCheckout;
     List<ProductCheckout> productCheckouts;

     /// <summary>
     /// 
     /// </summary>
     /// <param name="productCheckout"></param>
     /// <param name="promotions"></param>
     /// <returns></returns>
     public boolean CanExecute(ProductCheckout productCheckout, List<Promotion> promotions)
     {
         recentProductCheckout = productCheckout;
         appliedPromotion = promotions.stream().filter(x -> x.ProductCode.split(';').contains(productCheckout.ProductCode)).FirstOrDefault();
         if (appliedPromotion != null && !productCheckout.IsValidated && appliedPromotion.Type == PromotionTypeConstants.Combo)
         {
             return true;
         }

         return false;
     }


    
     public double CalculateProductPrice(List<ProductCheckout> productCheckoutList)
     {
         productCheckouts = new List<ProductCheckout>();

         double finalPrice = 0;


         try
         {
             String[] str = appliedPromotion.ProductCode.Split(';').ToArray();
             for (ProductCheckout item : productCheckoutList)
             {
                 if (str.Contains(item.ProductCode))
                 {
                     productCheckouts.add(item);
                     item.IsValidated = true;
                 }
             }

             int quantity_first = 0;
             int quantity_second = 0;
             if (productCheckouts.Count > 1)
             {
                 quantity_first = productCheckouts[0].Quantity;
                 quantity_second = productCheckouts[1].Quantity;
             }
             //if one of the product quatity is empty
             if (quantity_first == 0 || quantity_second == 0)
             {
                 return recentProductCheckout.DefaultPrice;

             }

             //if both of the products are equal is size
             if (quantity_first == quantity_second)
             {
                 finalPrice = appliedPromotion.Price * quantity_first;
             }
             else if (quantity_first > quantity_second)
             {
                 int additionalItems = quantity_first - quantity_second;
                 finalPrice = (recentProductCheckout.DefaultPrice * additionalItems) + (appliedPromotion.Price * quantity_second);
             }
             else if (quantity_first < quantity_second)
             {
                 int additionalItems = quantity_second - quantity_first;
                 finalPrice = (recentProductCheckout.DefaultPrice * additionalItems) + (appliedPromotion.Price * quantity_first);
             }
         }
         catch (ArithmeticException ex)
         {
             logger.log("Error in ComboOffer :" + ex.getMessage());
         }
         catch (Exception e)
         {
             logger.log("Error in ComboOffer :" + e.getMessage());
         }

         return finalPrice;
     }
}
