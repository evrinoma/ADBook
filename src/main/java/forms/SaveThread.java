package forms;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entity.CompanyDto;
import entity.UserDto;
import libs.Companys;
import libs.Core;


public class SaveThread extends SwingWorker<Object, String> {

	public static final String FILE_EXTENSION = ".xlsx";

	private static final String HINT_TRY_SAVE = "Сохраняем в файл....";
	
	private Companys companys;
	
	private Core core;

	private String file;
	
	public SaveThread(Core core)
	{
		this.core = core;
		this.companys = core.getCompanys();
	}
	
	
	
	@Override
	protected Boolean doInBackground() throws Exception {	
		publish(HINT_TRY_SAVE);
		return saveToFile();
	}	
	
	// Can safely update the GUI from this method.
	protected void done() {
		boolean status;
		try {
			// Retrieve the return value of doInBackground.
			status = (boolean) get();
			if (status) {
				core.setStatusString("["+file+"] written successfully on disk.");
			}
		} catch (InterruptedException e) {
			// This is thrown if the thread's interrupted.
		} catch (ExecutionException e) {
			// This is thrown if we throw an exception
			// from doInBackground.
		}
	}

	@Override
	// Can safely update the GUI from this method.
	protected void process(List<String> chunks) {
		// Here we receive the values that we publish().
		// They may come grouped in chunks.
		for (final String massage : chunks) {			
			core.setStatusString(massage);
		}
	}
	
	
	private Map<Integer, Object[]> createHeader(Map<Integer, Object[]> data) {		
		data.put(data.size()-1, new Object[] { "Фамилия", "Имя", "Отчество", "Номер", "Добавочный", "email", "Комната", "Компания",
				"Департамент", "Должность" });

		return data;
	}
	
	private Map<Integer, Object[]> createCompanyName(Map<Integer, Object[]> data, CompanyDto company) {		
		data.put(data.size()-1, new Object[] { "Компания:", company.getDescription()});

		return data;
	}

	private void setColumnsWidth(XSSFSheet sheet)
	{		
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4500);
		
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 3500);
		sheet.setColumnWidth(5, 5500);
		
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 15000);
		
		sheet.setColumnWidth(9, 15000);
	}
	
	private XSSFSheet createNewSheet(XSSFWorkbook workbook, String nameList) {		
		XSSFSheet sheet = workbook.getSheet(nameList);
		if (null == sheet) {
			sheet = workbook.createSheet(nameList);
		}
		
		return sheet;
	}

	private void createRows(XSSFSheet sheet, Map<Integer, Object[]> data) {
		int rownum = 0;
		for (Integer key : data.keySet()) {
			Row row = sheet.createRow(rownum++);			
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				}
			}
		}	
	}
	
	private void printCompany(XSSFWorkbook workbook, CompanyDto company, String nameCompany) {
		if (!company.getUsers().isEmpty()) {
			XSSFSheet sheet = createNewSheet(workbook, nameCompany);
			Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
			createCompanyName(data, company);
			createHeader(data);
			setColumnsWidth(sheet);		
			int count = data.size();
			for (UserDto user : company.getUsers().values()) {
				data.put(count++,
						new Object[] { user.getLastName(), user.getFirstName(), user.getMiddleName(),
								user.getOtherTelephone(), user.getTelephoneNumber(), user.getMail(),
								user.getPhysicalDeliveryOfficeName(), user.getCompany(), user.getDepartment(),
								user.getDescription() });
			}
			createRows(sheet, data);
		}
		if (!company.getFilials().isEmpty()) {
			for (CompanyDto filial : company.getFilials()) {
				printCompany(workbook, filial, filial.getOu()+"."+nameCompany);
			}
		}
	}

	public boolean saveToFile() {
		boolean status = false;
		XSSFWorkbook workbook = new XSSFWorkbook();
		Map<Integer, Object[]> data = null;

		for (CompanyDto company : companys.getCompanys()) {
			printCompany(workbook, company, company.getDescription());
		}

		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(this.file));
			workbook.write(out);
			out.close();			
			status = true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return status;
	}

	public void setFileName(String file) {
		this.file = file;		
	}
}
