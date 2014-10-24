 
/*****************************************************************************************
 * @file  TestTupleGenerator.java
 *
 * @author   Sadiq Charaniya, John Miller
 */

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Map;

/*****************************************************************************************
 * This class tests the TupleGenerator on the Student Registration Database defined in the
 * Kifer, Bernstein and Lewis 2006 database textbook (see figure 3.6).  The primary keys
 * (see figure 3.6) and foreign keys (see example 3.2.2) are as given in the textbook.
 */


public class TestTupleGenerator
{
    /*************************************************************************************
     * The main method is the driver for TestGenerator.
     * @param args  the command-line arguments
     */
	
	//the number of tables per test
	public static final int NUM_TABLES = 5;
	
	//the valid table names to index the tables with
	public static final String [] TABLE_NAMES = {"Student", "Professor", "Course", "Teaching", "Transcript" };
	
	//define the tables implementing treemap
	public static Table_TreeMap Student_TM = new Table_TreeMap ("Student", 
			"id name address status",
	        "Integer String String String",
	        "id");
	
	public static Table_TreeMap Professor_TM = new Table_TreeMap ("Professor",
            "id name deptId",
            "Integer String String",
            "id");
	public static Table_TreeMap Course_TM = new Table_TreeMap ("Course",
            "crsCode deptId crsName descr",
            "String String String String",
            "crsCode");
	
	public static Table_TreeMap Teaching_TM = new Table_TreeMap ("Teaching",
            "crsCode semester profId",
            "String String Integer",
            "crcCode semester"
	);
	
	public static Table_TreeMap Transcript_TM = new Table_TreeMap("Transcript",
            "studId crsCode semester grade",
            "Integer String String String",
            "studId crsCode semester"
    );
	
	public static Map <String, Table_TreeMap> TreeMap = new HashMap<String, Table_TreeMap>(); 
	static{
		TreeMap.put(TABLE_NAMES[0], Student_TM);
		TreeMap.put(TABLE_NAMES[1], Professor_TM);
		TreeMap.put(TABLE_NAMES[2], Course_TM);
		TreeMap.put(TABLE_NAMES[3], Teaching_TM);
		TreeMap.put(TABLE_NAMES[4], Transcript_TM);

	}
	//define the tables implementing ExtHashMap
	public static Table_ExtHashMap Student_EHM = new Table_ExtHashMap ("Student", 
			"id name address status",
	        "Integer String String String", 
	        "id");
	
	public static Table_ExtHashMap Professor_EHM = new Table_ExtHashMap ("Professor",
            "id name deptId",
            "Integer String String",
            "id");
	public static Table_ExtHashMap Course_EHM = new Table_ExtHashMap ("Course",
            "crsCode deptId crsName descr",
            "String String String String",
            "crsCode");
	
	public static Table_ExtHashMap Teaching_EHM = new Table_ExtHashMap ("Teaching",
            "crsCode semester profId",
            "String String Integer",
            "crcCode semester"
	);
	
	public static Table_ExtHashMap Transcript_EHM = new Table_ExtHashMap("Transcript",
            "studId crsCode semester grade",
            "Integer String String String",
            "studId crsCode semester"
    );
	
	public static Map <String, Table_ExtHashMap> ExtHashMap = new HashMap<String, Table_ExtHashMap>(); 
	static{
		ExtHashMap.put(TABLE_NAMES[0], Student_EHM);
		ExtHashMap.put(TABLE_NAMES[1], Professor_EHM);
		ExtHashMap.put(TABLE_NAMES[2], Course_EHM);
		ExtHashMap.put(TABLE_NAMES[3], Teaching_EHM);
		ExtHashMap.put(TABLE_NAMES[4], Transcript_EHM);
	}
	//define the table implementing BpTreeMap
	public static Table_BpTreeMap Student_BTM = new Table_BpTreeMap ("Student", "id name address status",
	        "Integer String String String", "id");
	
	public static Table_BpTreeMap Professor_BTM = new Table_BpTreeMap ("Professor",
            "id name deptId",
            "Integer String String",
            "id");
	public static Table_BpTreeMap Course_BTM = new Table_BpTreeMap ("Course",
            "crsCode deptId crsName descr",
            "String String String String",
            "crsCode");
	
	public static Table_BpTreeMap Teaching_BTM = new Table_BpTreeMap ("Teaching",
            "crsCode semester profId",
            "String String Integer",
            "crcCode semester"
	);
	
	public static Table_BpTreeMap Transcript_BTM = new Table_BpTreeMap("Transcript",
            "studId crsCode semester grade",
            "Integer String String String",
            "studId crsCode semester"
    );
	
	public static Map <String, Table_BpTreeMap> BpTreeMap = new HashMap<String, Table_BpTreeMap>(); 
	static{
		BpTreeMap.put(TABLE_NAMES[0], Student_BTM);
		BpTreeMap.put(TABLE_NAMES[1], Professor_BTM);
		BpTreeMap.put(TABLE_NAMES[2], Course_BTM);
		BpTreeMap.put(TABLE_NAMES[3], Teaching_BTM);
		BpTreeMap.put(TABLE_NAMES[4], Transcript_BTM);
	}
	
	//and finally define an array of hashmaps for easy iteration when we build the tables
	public static HashMap [] maps = {
		(HashMap) TreeMap,
		(HashMap) BpTreeMap,
		(HashMap) ExtHashMap,
	};
    public static void main (String [] args)
    {
    	TupleGenerator test = new TupleGeneratorImpl ();

        
        test.addRelSchema ("Student",
                           "id name address status",
                           "Integer String String String",
                           "id",
                           null);
        
        test.addRelSchema ("Professor",
                           "id name deptId",
                           "Integer String String",
                           "id",
                           null);
        
        test.addRelSchema ("Course",
                           "crsCode deptId crsName descr",
                           "String String String String",
                           "crsCode",
                           null);
        
        test.addRelSchema ("Teaching",
                           "crsCode semester profId",
                           "String String Integer",
                           "crcCode semester",
                           new String [][] {{ "profId", "Professor", "id" },
                                            { "crsCode", "Course", "crsCode" }});
        
        test.addRelSchema ("Transcript",
                           "studId crsCode semester grade",
                           "Integer String String String",
                           "studId crsCode semester",
                           new String [][] {{ "studId", "Student", "id"},
                                            { "crsCode", "Course", "crsCode" },
                                            { "crsCode semester", "Teaching", "crsCode semester" }});

        String [] tables = { "Student", "Professor", "Course", "Transcript", "Teaching" };
        
        //int tups [] = new int [] { 10000, 1000, 2000, 50000, 5000 };
        int tups [] = new int [] { 1, 2, 3, 4, 5 };

        Comparable [][][] resultTest = test.generate (tups);
        
        //build the tables
	        for (int i = 0; i < resultTest.length; i++) { //this loop controls how many tuples in the test
	            for (int j = 0; j < resultTest [i].length; j++) {
	            	TreeMap.get(tables[i]).insert(resultTest[i][j]);
	            	ExtHashMap.get(tables[i]).insert(resultTest[i][j]);
	            	BpTreeMap.get(tables[i]).insert(resultTest[i][j]);
	               // for (int k = 0; k < resultTest [i][j].length; k++) {
	              //      out.print (resultTest [i][j][k] + ",");
	              //  } // for
	                out.println ();
	            } // for
	            out.println ();
	        } // for
        
    } // main

} // TestTupleGenerator
