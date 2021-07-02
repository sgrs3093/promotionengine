package Entity;

import java.util.List;

import com.pro.model.ProductCheckout;

public class AppliedOffer

{

	private  List<ProductCheckout> checkouts;

	private  double totalPrice;

	public List<ProductCheckout> getCheckouts() {
		return checkouts;
	}

	public void setCheckouts(List<ProductCheckout> checkouts) {
		this.checkouts = checkouts;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	

}
