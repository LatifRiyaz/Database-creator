import java.util.*;
import java.sql.*;
public class operationsOfDB {
    
    static void createDB() throws Exception{
        Scanner in = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "opensql@007");
        Statement stmt = con.createStatement();
        System.out.println("\n------------------ Creating Database ------------------\n");
        try{
            System.out.print("Enter the name for database => ");
            String dbname = in.nextLine();
            String query = "create database "+dbname;
            stmt.executeUpdate(query);
            System.out.println("Success");
        }        
        catch(Exception e){
            System.out.println("Error in creating database, Try again");
        }
    }
    
    static void tableDB() throws Exception{
        Scanner in = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "opensql@007");
        Statement stmt = con.createStatement();
        System.out.println("\n------------------ Creating table ------------------\n");
        
        ResultSet rs = stmt.executeQuery("show databases");
        ArrayList<String> avail_db = new ArrayList<>();
        while(rs.next()){
            avail_db.add(rs.getString(1));
        }
        System.out.println("Available Databases : " + avail_db);
        
        int db_int = 0;
        String dbname = "";
        do{   
            System.out.print("Enter the name of database => ");
            dbname = in.nextLine();
            for(int i=0;i<avail_db.size();i++){
                if(avail_db.get(i).equals(dbname)){
                    
                    db_int = 0;
                    break;
                }
                else{
                    db_int = 1;
                }
            }
            
            if(db_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(db_int != 0);
        
        stmt.executeUpdate("use " + dbname);
        
        System.out.print("How many records are you going to enter => ");
        int size = in.nextInt();
        in.nextLine();
        
        ResultSet rst = stmt.executeQuery("show tables");
        ArrayList<String> avail_table = new ArrayList<>();
        while(rst.next()){
            avail_table.add(rst.getString(1));
        }
        
        System.out.println("Available Tables : " + avail_table);
        
        int table_int = 0;
        String table_name = "";
        do{   
            System.out.print("Enter the table => ");
            table_name = in.nextLine();
            for(int i=0;i<avail_table.size();i++){
                if(avail_table.get(i).equals(table_name)){
                    
                    table_int = 0;
                    break;
                }
                else{
                    table_int = 1;
                }
            }
            
            if(table_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(table_int != 0);
        
        try{            
            String[] fcn = new String[size];
            String[] scn = new String[size];
            System.out.println("Enter column name first and type after that\n(Press\'Enter\' Key after entering each record)");
            for(int i=0;i<size;i++){
                fcn[i] = in.nextLine();
                scn[i] = in.nextLine();
            }
            System.out.println(Arrays.toString(fcn));
            System.out.println(Arrays.toString(scn));
            
            String query_table = "create table "+table_name+"("+fcn[0]+" "+scn[0]+"(255));";
            stmt.executeUpdate(query_table);
            for(int i=1;i<size;i++){
                String query_table_a = "alter table "+table_name+" add "+fcn[i]+" "+scn[i]+"(255);";
                stmt.executeUpdate(query_table_a);
            }
            System.out.println("Success");
        }
        catch(Exception e){
            System.out.println("Error in creating table");
            String drop_table = "drop table "+table_name;
            stmt.executeUpdate(drop_table);
            System.out.println("Table dropped successfully");
        }
    }
    
    static void alterDBrecords() throws Exception{
        Scanner in = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "opensql@007");
        Statement stmt = con.createStatement();
        System.out.println("\n------------------ Altering table ------------------\n");
        
        ResultSet rs = stmt.executeQuery("show databases");
        ArrayList<String> avail_db = new ArrayList<>();
        while(rs.next()){
            avail_db.add(rs.getString(1));
        }
        System.out.println("Available Databases : " + avail_db);
        
        int db_int = 0;
        String dbname = "";
        do{   
            System.out.print("Enter the name of database => ");
            dbname = in.nextLine();
            for(int i=0;i<avail_db.size();i++){
                if(avail_db.get(i).equals(dbname)){
                    
                    db_int = 0;
                    break;
                }
                else{
                    db_int = 1;
                }
            }
            
            if(db_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(db_int != 0);
        
        stmt.executeUpdate("use " + dbname);
        ResultSet rst = stmt.executeQuery("show tables");
        ArrayList<String> avail_table = new ArrayList<>();
        while(rst.next()){
            avail_table.add(rst.getString(1));
        }
        
        System.out.print("Availabale Operations =>\n1. Add column\n2. Delete column\n3. Change column\nEnter the number to perform operations => ");
        int op_no = in.nextInt();
        System.out.println("Available Tables : " + avail_table);
        in.nextLine();
        
        int table_int = 0;
        String table_name = "";
        do{   
            System.out.print("Enter the table => ");
            table_name = in.nextLine();
            for(int i=0;i<avail_table.size();i++){
                if(avail_table.get(i).equals(table_name)){
                    
                    table_int = 0;
                    break;
                }
                else{
                    table_int = 1;
                }
            }
            
            if(table_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(table_int != 0);
        
        
        ArrayList<String> columns = new ArrayList<>();
        ResultSet rsc = stmt.executeQuery("show columns from "+table_name);
        while(rsc.next()){
            columns.add(rsc.getString(1));
        }
        
        System.out.println("Available columns in " +table_name + " is => "+columns);
        
        if(op_no == 1){
            System.out.println("----------- Adding columns to existing database ------------");
            System.out.print("Enter how many records you want to insert? => ");
            int totalRecords = in.nextInt();
            try{
                for(int i=0;i<totalRecords;i++){
                    System.out.print("Enter the name and type of the column with a gap => ");
                    String name_column = in.nextLine();
                    String type_column = in.nextLine();
                    String alter_insert = "alter table "+table_name+" add " +name_column+" "+type_column+"(255);";
                    stmt.executeUpdate(alter_insert);
                    System.out.println("Success");
                }
            }
            catch(Exception e){
                System.out.println("Error in adding records");
            }
        }
        
        else if(op_no == 2){
            System.out.print("Enter the column name to be deleted => ");
            String del_input = in.nextLine();
            try{
                String del_query = "alter table "+table_name+" drop column "+del_input;
                stmt.executeUpdate(del_query);
                System.out.println("Deleted column " + "\""+del_input+"\"" + " successfully");
            }
            catch(Exception e){
                System.out.println("Error in deleting");
            }
        }
        
        else if(op_no == 3){
            
            System.out.print("Type name to change column name and type to change column type\n(Note : Only Column name/type changes not the data)=> ");
            String choice = in.nextLine();
            
            if(choice.toLowerCase().equals("name")){
                System.out.print("Enter the old and new name in new line=> ");
                String old_name = in.nextLine();
                String new_name = in.nextLine();

                try{
                    String alter_name_query = "alter table "+table_name+" rename column "+old_name+" to " + new_name;
                    stmt.executeUpdate(alter_name_query);
                    System.out.println("Success");
                }
                catch(Exception e){
                    System.out.println("Error");
                }
            }
            
            else if(choice.toLowerCase().equals("type")){

                System.out.print("Enter the column name and new data type to be changed in new line => ");
                String alter_txt = in.nextLine();
                String alter_type = in.nextLine();
                try{
                    String alter_name_query = "alter table "+table_name+" modify column "+alter_txt+" " +alter_type+"(255)";
                    stmt.executeUpdate(alter_name_query);
                    System.out.println("Success");
                }
                catch(Exception e){
                    System.out.println("Error");
                }

            }
            
            else{
                System.out.println("Invalid entry");
            }
            
        }
        
        else{
            System.out.println("Invalid option");
        }
    }
    
    static void insertData() throws Exception{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "opensql@007");
        Scanner in = new Scanner(System.in);
        Statement stmt = con.createStatement();
        System.out.println("\n.......... Inserting data ..........\n");
        ResultSet rs = stmt.executeQuery("show databases");
        ArrayList<String> avail_db = new ArrayList<>();
        while(rs.next()){
            avail_db.add(rs.getString(1));
        }
        System.out.println("Available Databases : " + avail_db);

        int db_int = 0;
        String dbname = "";
        do{   
            System.out.print("Enter the name of database => ");
            dbname = in.nextLine();
            for(int i=0;i<avail_db.size();i++){
                if(avail_db.get(i).equals(dbname)){
                    
                    db_int = 0;
                    break;
                }
                else{
                    db_int = 1;
                }
            }
            
            if(db_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(db_int != 0);
        
        stmt.executeUpdate("use " + dbname);
        
        ResultSet rst = stmt.executeQuery("show tables");
        ArrayList<String> avail_table = new ArrayList<>();
        while(rst.next()){
            avail_table.add(rst.getString(1));
        }
        
        System.out.print("Available Operations =>\n1. Insert data\n2. Delete data\n3. Modify data\n4. Find duplicate and delete record\nEnter the number to perform operations => ");
        int op_no = in.nextInt();
        System.out.println("Available Tables : " + avail_table);
        in.nextLine();
        
        int table_int = 0;
        String table_name = "";
        do{   
            System.out.print("Enter the table => ");
            table_name = in.nextLine();
            for(int i=0;i<avail_table.size();i++){
                if(avail_table.get(i).equals(table_name)){
                    
                    table_int = 0;
                    break;
                }
                else{
                    table_int = 1;
                }
            }
            
            if(table_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(table_int != 0);
        
        ArrayList<String> columns = new ArrayList<>();
        ResultSet rsc = stmt.executeQuery("show columns from "+table_name);
        while(rsc.next()){
            columns.add(rsc.getString(1));
        }
        
        System.out.println("Available columns in " +table_name + " is => "+columns);
        
        if(op_no == 1){
            int size = columns.size();
            String[] inputs = new String[size];
            for(int i=0;i<size;i++){
                System.out.print(columns.get(i) + " => ");
                inputs[i] = in.nextLine();
            }
            try{
   
                for(int i=0;i<size;i++){
                    if(i==0){
                        String insert_query = "insert into " + table_name + "(" +columns.get(i)+ ") values(\'" + inputs[i]+ "\');";
                        stmt.executeUpdate(insert_query);
                        //System.out.println(insert_query);
                    }
                    else{
                        String update_query = "update "+table_name+" set "+columns.get(i)+"=\'"+inputs[i]+"\' where "+columns.get(0)+"=\'"+inputs[0]+"\';";
                        stmt.executeUpdate(update_query);
                    }
                }
                System.out.println("Success");
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        
        else if(op_no == 2){
           System.out.print("Enter the column name to use for deleting => ");
           String c_name = in.nextLine();
           System.out.print("Enter the data inside the column to delete => "); 
           String c_data = in.nextLine();
           try{
               String delete_query = "delete from "+table_name+" where "+c_name+" = \'"+c_data+"\'";
               stmt.executeUpdate(delete_query);
               Thread.sleep(1000);
               System.out.println("Done...Check your database once");
           
           }
           catch(Exception e){
               System.out.println("Error in deleting record");
           }
        }
        
        else if(op_no == 3){
            System.out.print("Enter the column name to be modified => ");
            String c_name = in.nextLine();
            System.out.print("Enter the new data which should be modified => ");
            String c_data = in.nextLine();
        }
        
        else if(op_no == 4){
            
            System.out.print("Enter column name => ");
            String c1 = in.nextLine();
            //select count(*) from student_records where name='Arjun' group by name having count(*)>1;
            
            String find_duplicate = "select "+ c1 + ", count(*) as 'Count' from "+table_name+" group by "+c1+" having count(*)>1";
            ResultSet rs1 = stmt.executeQuery(find_duplicate);
            String c1_data = "";
            while(rs1.next()){
                c1_data = rs1.getString(c1);
                String count = rs1.getString("Count");
                System.out.println("Repeated "+c1+" => "+c1_data + " for " + count + " times");
            }
            
            
            String display_query = "select * from "+table_name+" where "+c1+"=\'"+c1_data+"\'";
            ResultSet display = stmt.executeQuery(display_query);
            System.out.println("\n\t\t\t\t............. Available duplicate records .............\n");
            while(display.next()){
                for(int i=0;i<columns.size();i++){
                    String str = display.getString(columns.get(i));
                    System.out.print(columns.get(i) + " -> " + str + " || ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    
    static void display() throws Exception{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "opensql@007");
        Scanner in = new Scanner(System.in);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("show databases");
        ArrayList<String> avail_db = new ArrayList<>();
        while(rs.next()){
            avail_db.add(rs.getString(1));
        }
        System.out.println("Available Databases : " + avail_db);

        int db_int = 0;
        String dbname = "";
        do{   
            System.out.print("Enter the name of database => ");
            dbname = in.nextLine();
            for(int i=0;i<avail_db.size();i++){
                if(avail_db.get(i).equals(dbname)){
                    
                    db_int = 0;
                    break;
                }
                else{
                    db_int = 1;
                }
            }
            
            if(db_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(db_int != 0);
        
        stmt.executeUpdate("use " + dbname);

        ResultSet rst = stmt.executeQuery("show tables");
        ArrayList<String> avail_table = new ArrayList<>();
        while(rst.next()){
            avail_table.add(rst.getString(1));
        }
        System.out.println("Available tables in "+ dbname + " => " + avail_table);
        
        int table_int = 0;
        String table_name = "";
        do{   
            System.out.print("Enter the table => ");
            table_name = in.nextLine();
            for(int i=0;i<avail_table.size();i++){
                if(avail_table.get(i).equals(table_name)){
                    
                    table_int = 0;
                    break;
                }
                else{
                    table_int = 1;
                }
            }
            
            if(table_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(table_int != 0);
        
        ArrayList<String> columns = new ArrayList<>();
        ResultSet rsc = stmt.executeQuery("show columns from "+table_name);
        while(rsc.next()){
            columns.add(rsc.getString(1));
        }
        
        String display_query = "select * from "+table_name;
        ResultSet display = stmt.executeQuery(display_query);
        
        
        System.out.println("\n\t\t\t\t............. Available Records .............\n");
        while(display.next()){
            for(int i=0;i<columns.size();i++){
                String str = display.getString(columns.get(i));
                System.out.print(columns.get(i) + " -> " + str + " || ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    static void join() throws Exception{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "opensql@007");
        Scanner in = new Scanner(System.in);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("show databases");
        ArrayList<String> avail_db = new ArrayList<>();
        while(rs.next()){
            avail_db.add(rs.getString(1));
        }
        System.out.println("Available Databases : " + avail_db);

        int db_int = 0;
        String dbname = "";
        do{   
            System.out.print("Enter the name of database => ");
            dbname = in.nextLine();
            for(int i=0;i<avail_db.size();i++){
                if(avail_db.get(i).equals(dbname)){
                    
                    db_int = 0;
                    break;
                }
                else{
                    db_int = 1;
                }
            }
            
            if(db_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(db_int != 0);
        
        stmt.executeUpdate("use " + dbname);

        ResultSet rst = stmt.executeQuery("show tables");
        ArrayList<String> avail_table = new ArrayList<>();
        while(rst.next()){
            avail_table.add(rst.getString(1));
        }
        System.out.println("Available tables in "+ dbname + " => " + avail_table);
        
        int table_int = 0;
        String table_name1 = "";
        String table_name2 = "";
        do{   
            System.out.print("Enter the table 1 => ");
            table_name1 = in.nextLine();
            System.out.print("Enter the table 2 => ");
            table_name2 = in.nextLine();
            for(int i=0;i<avail_table.size();i++){
                if(avail_table.get(i).equals(table_name1) || avail_table.get(i).equals(table_name2)){
                    table_int = 0;
                    break;
                }
                else{
                    table_int = 1;
                }
            }
            
            if(table_int == 1){
                System.out.println("You have Entered invalid name");
            }
            
        }while(table_int != 0);

        System.out.print("Enter a new name for the table => ");
        String new_name = in.nextLine();
        String nj_query = "create table " + new_name + " select * from "+table_name1 + " natural join "+table_name2;
        
        stmt.executeUpdate(nj_query);
        
        ArrayList<String> new_table_columns = new ArrayList<>();
        
        String join_query = "show columns from "+new_name;
        
        ResultSet rs_col = stmt.executeQuery(join_query);
        
        while(rs_col.next()){
            new_table_columns.add(rs_col.getString(1));
        }
        
        String display_query = "select * from "+new_name;
        ResultSet display = stmt.executeQuery(display_query);
        
        
        System.out.println("\n\t\t\t\t............. After joining and storing in " + new_name + " .............\n");
        while(display.next()){
            for(int i=0;i<new_table_columns.size();i++){
                String str = display.getString(new_table_columns.get(i));
                System.out.print(new_table_columns.get(i) + " -> " + str + " || ");
            }
            System.out.println();
        }
        System.out.println();
        
    }
    
    public static void main(String args[]) throws Exception{
        Scanner in = new Scanner(System.in);
        Class.forName("com.mysql.cj.jdbc.Driver");
        int loop=0;
        do{
            System.out.print("Database Operations available\nEnter 1 to create database\nEnter 2 to create tables"
                    + "\nEnter 3 to alter any record\nEnter 4 to perform operation in insert\nEnter 5 to display all records"
                    + "\nEnter 6 to join tables\nEnter the number your choice => ");
            int input = in.nextInt();
        
            switch(input){
                case 1:
                    createDB();
                    break;
                case 2:
                    tableDB();
                    break;
                case 3:
                    alterDBrecords();
                    break;
                case 4:
                    insertData();
                    break;
                case 5:
                    display();
                    break;
                case 6:
                    join();
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
            System.out.print("\nTo continue enter 1(one) / To exit enter 0(zero) => ");
            loop = in.nextInt();
            if(loop == 0){
                System.out.println("GoodBye");
            }
        }while(loop!=0);
    }

}
