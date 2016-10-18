package exameval.domain.svg;

public class SVGLine extends SVGObject{
	
	double x1;
	double y1;
	double x2;
	double y2;

	public SVGLine(double x1, double y1, double x2, double y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public double getX1(){
		return this.x1;
	}
	
	public double getY1(){
		return this.y1;
	}
	
	public double getX2(){
		return this.x2;
	}
	
	public double getY2(){
		return this.y2;
	}

	public boolean isCloseToEnd(double x, double y, double tolerance, int end) {
		boolean check = false;
		if(end==0){
			if(Math.sqrt((x-this.x1)*(x-this.x1)+(y-this.y1)*(y-this.y1)) < tolerance)
				check = true;
	        else
	        	check = false;
		}
		else if(end==1){
			if(Math.sqrt((x-this.x2)*(x-this.x2)+(y-this.y2)*(y-this.y2)) < tolerance)
				check = true;
	        else
	        	check = false;
		}
		else{
			
		}
		
		return check;
	}
	
	@Override
	public boolean isOnBoundry(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIn(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void print() {
		System.out.println("{x1=" + this.x1 + " ,y1=" + this.y1 + " ,x2=" + this.x2 + " ,y2=" + this.y2 + "}" );
	}

}
