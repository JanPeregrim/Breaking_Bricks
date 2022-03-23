package gamestudio.core;

import java.sql.DriverManager;

public class TestJDBC {
    public static void main(String[] args)throws Exception{
        try(var conenection =DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio","postgres","admin");
            var statement=conenection.createStatement();
            var rs=statement.executeQuery("SELECT player,game,points,played_at FROM score WHERE game='breaking bricks' ORDER BY points DESC LIMIT 10");
        )
        {
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
            //statement.executeUpdate("INSERT INTO score (player,game,points,played_at) VALUES ('breaking bricks','Janes','101','2022-03-10 14:30');");
        }
        System.out.println("-----------------");
    }
}
