import spark.ModelAndView;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


//import java.util.List;
//import java.util.ArrayList;



public class App {
     public static void main(String[] args) {
         staticFileLocation("/public");
         String layout = "templates/layout.vtl";

         ProcessBuilder process = new ProcessBuilder();
         Integer port;
         if (process.environment().get("PORT") != null) {
             port = Integer.parseInt(process.environment().get("PORT"));
         } else {
             port = 4567;
         }
         port(port);


         get("/", (request, response) -> {
             Map<String, Object> model = new HashMap<String, Object>();
             model.put("template", "templates/addnewhero.vtl");
             return new ModelAndView(model, layout);
         },  new VelocityTemplateEngine());

         post("/heroes",(request,response)->{
             Map <String,Object> model = new HashMap<String,Object>();
             String name =request.queryParams("name");
             int age = Integer.parseInt(request.queryParams("age"));
             String specialPowers =request.queryParams("specialPowers");
             String weakness =request.queryParams("weakness");
             Hero newHero = new Hero(name,age,specialPowers,weakness);
             model.put("template","templates/success.vtl");
             return new ModelAndView(model,layout);
         },new VelocityTemplateEngine());

         get("/heroes",(request,response)->{
             Map <String,Object> model = new HashMap<String,Object>();
             model.put("heroes",Hero.all());
             model.put("template","templates/heroes.vtl");
             return new ModelAndView(model,layout);
         },new VelocityTemplateEngine());

         get("/hero/:id",(request,response)->{
             Map<String,Object>	model = new HashMap<String,Object>();
             Hero hero =Hero.find(Integer.parseInt(request.params(":id")));
             model.put("hero",hero);
             model.put("template","templates/hero.vtl");

             return new ModelAndView(model,layout);
         },new VelocityTemplateEngine());
     }
}
