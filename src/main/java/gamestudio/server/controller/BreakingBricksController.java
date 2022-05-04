package gamestudio.server.controller;


import gamestudio.core.Brick;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import gamestudio.core.Field;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.context.annotation.Scope;

import org.springframework.web.context.WebApplicationContext;

import java.util.Date;


@Controller
@RequestMapping("/breakingBricks")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BreakingBricksController {

    private Field field = new Field(0, 0, 1);

    @RequestMapping
    public String breakingBricks(@RequestParam(required = false) Integer row,@RequestParam(required = false) Integer column){
        if(row!=null && column!=null){
            field.destroyBrick(row,column);
        }
        return "breakingBricks";
    }

    @RequestMapping("/new")
    public String newGame(){
        field=new Field(9,9,1);
        return "breakingBricks";
    }

    public String geCurrentTime(){
        return new Date().toString();
    }


    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                var brick = field.getBrick(row, column);
                sb.append("<td>\n");
                sb.append("<a href='/breakingBricks?row=" + row + "&column=" + column + "'>\n");
                sb.append("<img src='/images/" + getBrickImage(brick) + ".png'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    private String getBrickImage(Brick brick) {
        switch (brick.getType()){
            case B:
                return "blue";
            case G:
                return "green";
            case R:
                return "red";
        }
        return "empty";
    }
}
