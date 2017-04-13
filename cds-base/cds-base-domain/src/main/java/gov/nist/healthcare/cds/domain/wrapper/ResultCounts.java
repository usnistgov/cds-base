package gov.nist.healthcare.cds.domain.wrapper;

public class ResultCounts {

	private int p;
	private int f;
	private int u;
	private int w;
	
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}
	public int getU() {
		return u;
	}
	public void setU(int u) {
		this.u = u;
	}
	
	public void addP(){
		this.p++;
	}
	public void addF(){
		this.f++;
	}
	public void addU(){
		this.u++;
	}
	public void addW(){
		this.w++;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public void addCounts(ResultCounts c){
		this.p += c.p;
		this.f += c.f;
		this.u += c.u;
		this.w += c.w;
	}
	
	public void consider(Criterion c){
		switch(c.getStatus()){
		case P :
			this.addP();
			break;
		case F :
			this.addF();
			break;
		case U : 
			this.addU();
			break;
		case W : 
			this.addW();
			break;
		default:
			break;
		}
	}
	
}
