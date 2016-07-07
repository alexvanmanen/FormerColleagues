package nl.hsleiden.informatica;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TextEditorView extends Application {

	private static volatile Instrumentation globalInstr;

	public static void main(String[] args) {
		// Reflections reflections = new Reflections("nl.hsleiden.informatica");
		//
		// Set<Class<? extends Colleague>> classes =
		// reflections.getSubTypesOf(Colleague.class);
		// ArrayList<Class> instantiatedDerivedTypes;
		//
		// Class derivedClass = Colleague.class;
		// if (!instantiatedDerivedClass.contains(derivedClass)) {
		// instantiatedDerivedClass.Add(derivedClass);
		// }

		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		// Class[] allLoadedClasses = globalInstr.getAllLoadedClasses();
		// System.out.println(allLoadedClasses[0].getName());
		// for(Class c: allLoadedClasses){
		// if (c.isInstance()) {
		//
		// }
		// }

		primaryStage.setTitle("Bye Bye");
		GridPane grid = new GridPane();
		primaryStage.setX(550);
		primaryStage.setY(133);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 500, 500);
		primaryStage.setScene(scene);

		Text scenetitle = new Text("Bye Bye");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		final TextArea textArea = new TextArea();
		textArea.setEditable(false);

		grid.add(textArea, 1, 4, 3, 3);


		List<String> colleagueNames = ColleagueFactory.getInstance().getColleagueNames();
		Text classText = new Text();
		classText.setText("Colleague");
		Text methodText = new Text();
		methodText.setText("Method");
		ChoiceBox<String> cb1 = new ChoiceBox<String>(FXCollections.observableArrayList(colleagueNames));

		ChoiceBox<String> cb2 = new ChoiceBox<String>((FXCollections.observableArrayList(getMethodsOfCollegue())));

		grid.add(classText, 1, 1, 1, 1);
		grid.add(cb1, 2, 1, 1, 1);

		grid.add(methodText, 1, 2, 1, 1);
		grid.add(cb2, 2, 2, 1, 1);

		cb2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String colleagueChoice = cb1.getValue();
				String methodChoice = cb2.getValue();
				Colleague colleague = ColleagueFactory.getInstance().createColleague(colleagueChoice);
				textArea.setText(getValueFromMethod(methodChoice, colleague));
			}

			private String getValueFromMethod(String methodChoice, Colleague colleague) {
				String result = "";
				Method m;
				try {
					m = Class.forName(Colleague.class.getName()).getDeclaredMethod(methodChoice);
					Object r = m.invoke(colleague, null);
					
					if(r instanceof Date && r != null){
						SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
						sdf.setTimeZone(TimeZone.getTimeZone("CEST"));
						result = sdf.format((Date)r);
					} else {
						result = r.toString();
					}
					
				} catch (NotImplementedException ex) {
					result = "Helaas, deze methode is niet geimplementeerd (NotImplementedException).";
				} catch (NullPointerException ex) {
					result = "Helaas, deze methode returned null (NullPointerException).";
				} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					result = "Helaas, er iets mis met reflecteren van de methode. \nIk vermoed dat deze methode niet is geimplementeerd door " + colleague.getClass().getSimpleName();
				}

				return result;
			}
		});
		primaryStage.show();
	}

	private List<String> getMethodsOfCollegue() {
		Method[] methods = Colleague.class.getMethods();
		List<String> list = new LinkedList<String>();
		for (Method m : methods) {
			list.add(m.getName());
		}
		return list;
	}
}