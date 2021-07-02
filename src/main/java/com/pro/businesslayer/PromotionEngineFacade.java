package com.pro.businesslayer;

import java.util.List;

import com.pro.service.Logger;
import com.pro.service.PromotionServiceImpl;

public class PromotionEngineFacade {
	
	Logger logger = LoggerFactory.getLogger(PromotionServiceImpl.class);
	
	IPromotionInputOutput consoleLayer;
    IPromotionService promotionService;        
    IRepository configManagement;

    List<ProductCheckout> checkoutList;
    AppliedOffer appliedOffer;

    public Facade()
    {
        consoleLayer = new ConsoleLayer();
        promotionService = new PromotionService();
    }

    public boolean CheckoutProducts()
    {
        try
        {
            checkoutList = consoleLayer.LoadUserInput();
            return true;
        }
        catch (Exception ex)
        {
            LogWriter.LogWrite("Error in Checking out Products :" + ex.Message);
        }
        return false;
    }

    internal boolean ApplyPromotion()
    {
        try
        {
            appliedOffer = promotionService.ApplyPromotion(checkoutList, GetProductOffers());
            return true;
        }
        catch (Exception ex)
        {
            LogWriter.LogWrite("Error in  Applying Promotio :" + ex.Message);
        }
        return false;
    }

    public boolean DisplayTotalPrice()
    {
        try
        {
            if (appliedOffer.Checkouts != null)
            {
                consoleLayer.DisplayTotalPrice(appliedOffer);
                return true;
            }
        }
        catch (Exception ex)
        {
            LogWriter.LogWrite("Error in  Displaying TotalPrice:" + ex.Message);
        }

        return false;
    }
    public List<Promotion> GetProductOffers()
    {
        try
        {
            configManagement = new ConfigRepository();
            return configManagement.GetProductOffers();
        }
        catch (Exception ex)
        {
            LogWriter.LogWrite("Error in Getting Product Offers :" + ex.Message);
        }
        return new List<Promotion>();
    }

    public List<Product> GetAvilableProducts()
    {
        try
        {
            configManagement = new ConfigRepository();
            return configManagement.GetAvilableProducts();
        }
        catch (Exception ex)
        {
            LogWriter.LogWrite("Error in Getting AvilableProducts :" + ex.Message);

        }
        return new List<Product>();
    }
}
}
