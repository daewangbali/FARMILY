package org.kosta.myproject.vo;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@EntityScan
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FileVO {
	private int id;
	private String title;
	private String content;
	private String filename;
	private String filepath;
	private int board_no;

	
}
