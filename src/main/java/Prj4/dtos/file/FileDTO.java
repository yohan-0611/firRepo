package Prj4.dtos.file;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileDTO {
	
	private String idx;
	private String title;
	private String cate;
	private String ofile;
	private String sfile;
	private Date postdate;
}
