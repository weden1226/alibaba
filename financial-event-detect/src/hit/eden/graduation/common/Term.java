package hit.eden.graduation.common;

public class Term {

	private String word;
	private String pos;
	private double weight = 1.0;
	
	public Term(String word, String pos, double weight){
		this.word = word;
		this.pos = pos;
		this.weight = weight;
	}
	
	public String getWord(){
		return this.word;
	}
	
	public String getPos(){
		return this.pos;
	}
	
	public double getWeight(){
		return this.weight;
	}
	
	
}
