import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Train_Reservation_System {
    private static final String url="jdbc:mysql://localhost:3306/Train_db";
    private static final String userName= "root";
    private  static  final String passWord= "@aryan@2014";

    public static void main(String[] args) throws ClassNotFoundException , SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.driver");

        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection connection = DriverManager.getConnection(url,userName,passWord);
            Statement statement = connection.createStatement();
            while(true){
                System.out.println("TRAIN RESERVATION SYSTEM");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a Seat ");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Seat Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Cancel Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option :");
                int option = scanner.nextInt();
                switch (option){
                    case 1 :{
                     reserveSeat(connection,scanner,statement);
                     break;
                    }
                    case 2: {
                      viewReservaion(connection,statement);
                      break;
                    }
                    case 3: {
                     getSeatNumber(connection,scanner,statement);
                     break;
                    }
                    case 4: {
                    updateReservation(connection,scanner,statement);
                    break;
                    }
                    case 5: {
                    cancelReservation(connection,scanner,statement);
                    }
                    case 0: {
                      exit();
                      scanner.close();
                      return;
                    }
                    default:{
                        System.out.println("Entered Invalid Option...Try again.");
                    }

                }
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    private static void reserveSeat(Connection connection , Scanner scanner,Statement statement){
        System.out.print("Enter Passenger Name: ");
        String passengerName = scanner.next();
        scanner.nextLine();
        System.out.print("Enter The Bogie Number: ");
        String bogieNum= scanner.nextLine();
        System.out.print("Enter Seat No: ");
        int seatNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Contact No: ");
        String contactNum=scanner.next();

        String query = "INSERT INTO reservaion (passenger_name, seat_number, bogie_number, contact_number) VALUES ('"
                + passengerName + "', "
                + seatNumber + ", '" // Add single quotes here
                + bogieNum + "', '"
                + contactNum + "')";
        try{
            int affectedRows = statement.executeUpdate(query);
            if(affectedRows>0){
                System.out.println("Reservation Successful.");
            }else{
                System.out.println("Reservation Failed!!!");
            }
        }catch(SQLException e){
           e.printStackTrace();
        }

    }
    private static void viewReservaion(Connection connection, Statement statement) throws SQLException{
        String query = "SELECT reservation_id,passenger_name,seat_number,bogie_number,contact_number,reservation_date FROM reservaion";
        try{
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Current Reservations");
            System.out.println("+-----------------+---------------------+--------------+--------------+------------------+-------------------------+");
            System.out.println("| Reservation ID  |   Passenger Name    |  Seat Number | Bogie Number |  Contact Number  |  Reservation Date       |");
            System.out.println("+-----------------+---------------------+--------------+--------------+------------------+-------------------------+");
            while(resultSet.next()){
                int reservationId = resultSet.getInt("reservation_id");
                String passengerName = resultSet.getString("passenger_name");
                int seatNumber = resultSet.getInt("seat_number");
                String bogieNumber = resultSet.getString("bogie_number");
                String contactNumber = resultSet.getString("contact_number");
                String timeStemp = resultSet.getTimestamp("reservation_date").toString();
                System.out.printf("| %-15d | %-19s | %-12d | %-12s | %-16s | %-11s   |\n",
                        reservationId,passengerName,seatNumber,bogieNumber,contactNumber,timeStemp);
            }
            System.out.println("+-----------------+---------------------+--------------+--------------+------------------+-------------------------+");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void getSeatNumber(Connection connection,Scanner scanner,Statement statement){
        System.out.print("Enter Reservation ID :");
        int reservationId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Passenger Name :");
        String passengerName = scanner.nextLine();
        String query = "SELECT seat_number FROM reservaion " +
                "WHERE reservation_id = " + reservationId +
                " AND passenger_name = '" + passengerName + "'";
        try{
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int seatNumber = resultSet.getInt("seat_number");
                System.out.println("Seat Number for Reservation ID " + reservationId +
                        " and Passenger Name " + passengerName + " is " + seatNumber);
            } else {
                System.out.println("Reservation not found for the given ID and Passenger Name");
            }
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }
    private static  void updateReservation(Connection connection,Scanner scanner,Statement statement){
        System.out.print("Enter the reservation Id :");
        int reservationId = scanner.nextInt();
        scanner.nextLine();
        if(!reservationExists(connection,statement,reservationId)){
            System.out.println("Reservation not found for the given ID.");
            return;
        }
        System.out.print("Enter New Passenger Name :");
        String newPassName = scanner.nextLine();
        System.out.println("Enter Bogie Number :");
        String newBogieNum = scanner.nextLine();
        System.out.println("Enter New Seat Number :");
        int newSeatName = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter New Contact :");
        String newContact = scanner.nextLine();

        String query = "UPDATE reservaion SET passenger_name = '" + newPassName +
                "', bogie_number = '" + newBogieNum +
                "', seat_number = " + newSeatName +
                ", contact_number = '" + newContact +
                "' WHERE reservation_id = " + reservationId;

        try{
            int affectedRows = statement.executeUpdate(query);
            if(affectedRows>0){
                System.out.println("Reservation Updated Successfully.");
            }else{
                System.out.println("Reservation Update Failed!!!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    private static void cancelReservation(Connection connection, Scanner scanner, Statement statement){

        try{
            System.out.println("Enter the Reservation Id for Canceling.");
            int reservationId = scanner.nextInt();
            if(!reservationExists(connection,statement,reservationId)){
                System.out.println("Reservation Not Found For the given ID");
                return;
            }
            String query = "DELETE FROM reservaion WHERE reservation_id = "+reservationId;
            int affectedRows=statement.executeUpdate(query);
            if(affectedRows>0){
                System.out.println("Reservation Cancelled Succesfully.");
            }else{
                System.out.println("Reservation Cancellation Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean reservationExists(Connection connection ,Statement statement, int reservationId){
        try{
            String query = "SELECT reservation_id FROM reservaion WHERE reservation_id = "+reservationId;
            try {
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet.next();
            }finally {

            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i =5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank You For Using Train Reservation System...");
    }
}