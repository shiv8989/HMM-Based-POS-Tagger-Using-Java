/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlpassignment2;

/**
 *
 * @author Shiv
 */

import java.text.DecimalFormat;

public class Tag {
	String tagname;
	int tagIndex;
	int tp;
	int tn;
	int fp;
	int fn;
	double precision;
	double recall;
	double fmeasure;
	private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");

	public Tag getObject(int tagIndex) {
		return this;
	}

	public int getTagIndex() {
		return tagIndex;
	}

    public void setTagIndex(int tagIndex) {
        this.tagIndex = tagIndex;
    }

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public int getTp() {
		return tp;
	}

	public void setTp(int tp) {
		this.tp = tp;
	}

	public int getTn() {
		return tn;
	}

	public void setTn(int tn) {
		this.tn = tn;
	}

	public int getFp() {
		return fp;
	}

	public void setFp(int fp) {
		this.fp = fp;
	}

	public int getFn() {
		return fn;
	}

	public void setFn(int fn) {
		this.fn = fn;
	}

	public double getPrecision() {
		if (getTp() + getFp() == 0)
			return 0.0;
		double prec= Double.parseDouble(DECIMALFORMAT
				.format((double) getTp() / (getTp() + getFp())));
		return (prec);
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

	public double getRecall() {
		if (getTp() + getFn() == 0)
			return 0.0;
		double rec= Double.parseDouble(DECIMALFORMAT
				.format((double) getTp() / (getTp() + getFn())));
		return (rec);
	}

	public void setRecall(double recall) {
		this.recall = recall;
	}

	public double getFmeasure() {
		if (getPrecision() + getRecall() == 0)
			return 0.0;
		double fm= Double.parseDouble(DECIMALFORMAT
				.format((double) (2 * getPrecision() * getRecall()) / (getPrecision() + getRecall())));
		return (fm);
	}

	public void setFmeasure(double fmeasure) {
		this.fmeasure = fmeasure;
	}
}


