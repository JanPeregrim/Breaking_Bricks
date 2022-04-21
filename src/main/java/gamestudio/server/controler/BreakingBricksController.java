package gamestudio.server.controler;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import gamestudio.core.Field;
import gamestudio.core.Brick;


@Controller
@RequestMapping("/breaking bricks")
@Scope(WebApplicationContext.SCOPE_SESSION)

public class BreakingBricksController {
    private Field field = new Field(9, 9, 10);


}
