package com.sunsea.anno;

@DBMapping(type="Table",value="score")
public class ScoreDAO implements DAOInterface {
	
	@DBMapping(type="Column",value="id")
	private Integer id = null;
	
	@DBMapping(type="Column",value="chinese")
	private Integer chineseScore;
	
	@DBMapping(type="Column",value="math")
	private Integer mathScore;
	
	@DBMapping(type="Column",value="english")
	private Integer engScore;
	
	@DBMapping(type="Column",value="rank")
	private Integer rank;
	
	@DBMapping(type="Column",value="comment")
	private String comment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChineseScore() {
		return chineseScore;
	}

	public void setChineseScore(Integer chineseScore) {
		this.chineseScore = chineseScore;
	}

	public Integer getMathScore() {
		return mathScore;
	}

	public void setMathScore(Integer mathScore) {
		this.mathScore = mathScore;
	}

	public Integer getEngScore() {
		return engScore;
	}

	public void setEngScore(Integer engScore) {
		this.engScore = engScore;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
}
