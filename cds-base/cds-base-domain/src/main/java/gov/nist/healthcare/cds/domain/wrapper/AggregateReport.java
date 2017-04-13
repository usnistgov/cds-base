package gov.nist.healthcare.cds.domain.wrapper;

import java.util.HashMap;
import java.util.Map;

import gov.nist.healthcare.cds.enumeration.ValidationCriterion;

public class AggregateReport {

	private Map<ValidationCriterion, ResultCounts> counts;
	private int total;
	private int errors;
	private int correct;
	
	public AggregateReport() {
		super();
		counts = new HashMap<>();
	}

	public Map<ValidationCriterion, ResultCounts> getCounts() {
		return counts;
	}

	public void setCounts(Map<ValidationCriterion, ResultCounts> counts) {
		this.counts = counts;
	}
	
	public void add(ValidationCriterion criterion, Criterion ctr){
		ResultCounts c;
		if(counts.containsKey(criterion)){
			c = counts.get(criterion);
		}
		else {
			c = new ResultCounts();
		}
		c.consider(ctr);
		counts.put(criterion, c);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}
	
	
	
}
