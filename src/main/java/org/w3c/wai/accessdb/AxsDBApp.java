package org.w3c.wai.accessdb;

import org.w3c.wai.accessdb.services.DBInitService;
import org.w3c.wai.accessdb.services.TestResultsService;
import org.w3c.wai.accessdb.services.TestsService;

public class AxsDBApp {
	public static void main(String[] args) {
		if(args.length!=1){
			System.out.print("This app need 1 and only 1 argument.");
			System.exit(-1);
		}
		String cmd = args[0];
		switch (cmd) {
		case "init-all":
			try{
				DBInitService.INSTANCE.initAll();
				System.out.println("Init done!");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "export-tests-all":
			try{
				TestsService.INSTANCE.exportAllTests();
				System.out.println("Export done!");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "export-tests-results-all":
			try{
				TestResultsService.INSTANCE.exportAll();
				System.out.println("Export done!");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			printHelp();
			break;
		}
		
	}
	public static void printHelp(){
		System.out.println("This is the help;");
		System.out.println("init for init all db");
		System.out.println("export-tests-all for exporting tests");
		System.out.println("export-tests-results-all for exporting test results");

	}
}
