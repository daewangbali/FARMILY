package org.kosta.myproject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
	private int boardNo;
	private String title;
	private String content;
	private String selectCategori;
	private String boardCategori;
	private String region;
	private String createdDate;
	private String id;
}
