package co.edu.unal.gae.client;

import java.util.ArrayList;

import co.edu.unal.gae.shared.FieldVerifier;
import co.edu.unal.gae.shared.LoginInfo;
//import co.edu.unal.gae.shared.many2many.ofy.Alumno;
import co.edu.unal.gae.shared.ofytable.OfyTable;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gae implements EntryPoint {
	
	
	// TODO #05: add constants for OAuth2 (don't forget to update GOOGLE_CLIENT_ID)
		private static final Auth AUTH = Auth.get();
		//private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
		//private static final String GOOGLE_CLIENT_ID = "808558447093-1fa4phmdplj0lc7ug6n988u4gfageph8.apps.googleusercontent.com";
		//private static final String PLUS_ME_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
		
		private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
		private static final String GOOGLE_CLIENT_ID = "424307068100-b5ki5fn1hl5nog4vnkvl9p0jc7f162lo.apps.googleusercontent.com";
		private static final String PLUS_ME_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
		
		// TODO #05:> end

		// TODO #06: define controls for login
		private final HorizontalPanel loginPanel = new HorizontalPanel();
		private final Anchor signInLink = new Anchor("");
		private final Image loginImage = new Image();
		private final TextBox nameField = new TextBox();
		// TODO #06:> end
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	// TODO #07: add helper methods for Login, Logout and AuthRequest

	private void loadLogin(final LoginInfo loginInfo) {
		signInLink.setHref(loginInfo.getLoginUrl());
		signInLink.setText("Please, sign in with your Google Account");
		signInLink.setTitle("Sign in");
		//btnAgregar.setEnabled(false);
	}

	private void loadLogout(final LoginInfo loginInfo) {
		signInLink.setHref(loginInfo.getLogoutUrl());
		signInLink.setText(loginInfo.getName());
		signInLink.setTitle("Sign out");
	}

	private void addGoogleAuthHelper() {
		final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL, GOOGLE_CLIENT_ID)
				.withScopes(PLUS_ME_SCOPE);
		AUTH.login(req, new Callback<String, Throwable>() {
			@Override
			public void onSuccess(final String token) {
				btnAgregar.setEnabled(true);

				if (!token.isEmpty()) {
					greetingService.loginDetails(token, new AsyncCallback<LoginInfo>() {
						@Override
						public void onFailure(final Throwable caught) {
							GWT.log("loginDetails -> onFailure");
						}

						@Override
						public void onSuccess(final LoginInfo loginInfo) {
							signInLink.setText(loginInfo.getName());
							nameField.setText(loginInfo.getName());
							signInLink.setStyleName("login-area");
							loginImage.setUrl(loginInfo.getPictureUrl());
							loginImage.setVisible(false);
							loginPanel.add(loginImage);
							loginImage.addLoadHandler(new LoadHandler() {
								@Override
								public void onLoad(final LoadEvent event) {
									final int newWidth = 24;
									final com.google.gwt.dom.client.Element element = event
											.getRelativeElement();
									if (element.equals(loginImage.getElement())) {
										final int originalHeight = loginImage.getOffsetHeight();
										final int originalWidth = loginImage.getOffsetWidth();
										if (originalHeight > originalWidth) {
											loginImage.setHeight(newWidth + "px");
										} else {
											loginImage.setWidth(newWidth + "px");
										}
										loginImage.setVisible(true);
									}
								}
							});
						}
					});
				}
			}

			@Override
			public void onFailure(final Throwable caught) {
				GWT.log("Error -> loginDetails\n" + caught.getMessage());
			}
		});
	}
	


	private VerticalPanel principal = new VerticalPanel();
	private Label lblTitulo = new Label("Titulo: ");
	private TextBox txtTitulo = new TextBox();
	private Label lblAutor = new Label("Autor: ");
	private TextBox txtAutor = new TextBox();
	private Label lblEditorial = new Label("Editorial: ");
	private ListBox cboEditorial = new ListBox();
	private Label lblCopias = new Label("Copias: ");
	private ListBox cboCopias = new ListBox();
	private Label lblEstado = new Label("Estado: ");
	private RadioButton rbtA = new RadioButton("categoria", "Malo");
	private RadioButton rbtB = new RadioButton("categoria", "Regular");
	private RadioButton rbtC = new RadioButton("categoria", "Bueno");
	private Button btnAgregar = new Button("Agregar mi solicitud");
	
	private Label lblTiempo = new Label();
	// Componente tipo tabla que nos permite agregar componentes
	// le decimos el numero de filas y de columnas que va a tener
	private Grid gridPrincipal = new Grid(6, 2);
	// Tabla principal
	private FlexTable tablaAlumnos = new FlexTable();
	// Array con datos
	//private ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
	//Indice para saber si se esta editando
	private int indiceEditar = -1;
	//Codigo del alumno que registro
	private int codigo=1;


	private void llenarFacultades() {
		  this.cboEditorial.addItem("eLibros");
		  this.cboEditorial.addItem("Random House Mondadori");
		  this.cboEditorial.addItem("Alfaguara");

		}



	// Llena el combo de Escuelas en funcion de la Facultad
	private void llenarEscuelas() {
	  //Limpiamos el combo de la escuela
	  this.cboCopias.clear();
	  switch (cboEditorial.getSelectedIndex()) {
	  case 0:
	    this.cboCopias.addItem("1");
	    break;
	  case 1:
	    this.cboCopias.addItem("2");

	    break;
	  case 2:
	    this.cboCopias.addItem("3");
	    break;
	  
	  }
	}



	//Limpia los datos del formulario
	private void limpiar() {
	  this.txtTitulo.setText("");
	  this.txtAutor.setText("");
	  this.cboEditorial.setSelectedIndex(0);
	  this.llenarEscuelas();
	  this.rbtA.setValue(true);
	  this.indiceEditar = -1;
	}



	
	//Agregar un alumno a la tabla o modificarlo
	private void agregarAlumno() {
	 String nombre = this.txtTitulo.getText().toUpperCase().trim();
	 String apellido = this.txtAutor.getText().toUpperCase().trim();
	 String facultad = this.cboEditorial.getItemText(this.cboEditorial.getSelectedIndex());
	 String escuela = this.cboCopias.getItemText(this.cboCopias.getSelectedIndex());
	 int copias =1;
	 // Que solo permita letras y 20 caracteres
	 if (!nombre.matches("^[A-Z\\.\\ ]{1,20}$")) {
	  // Manda mensaje alerta tipo JavaScrip
	  //Window.alert("Nombre no valido");
	  this.txtTitulo.selectAll();
	  // Salimos del metodo
	  return;
	 }
	 if (!apellido.matches("^[A-Z\\.\\ ]{1,20}$")) {
	   // Manda mensaje alerta tipo JavaScrip
	  // Window.alert("Apellido no valido");
	   this.txtAutor.selectAll();
	   return;
	 }
	 String categoria;
	 double monto;
	 if (this.rbtA.getValue()) {
	  categoria = this.rbtA.getText();
	  monto = 440;
	 } else if (this.rbtB.getValue()) {
	  categoria = this.rbtB.getText();
	  monto = 400;
	 } else {
	  categoria = this.rbtC.getText();
	  monto = 330;
	 }
	 //Creamos la clase que vamos a registrar o a modificar
	 final OfyTable alu = new OfyTable(codigo, 
	        nombre, 
	    apellido, 
	    facultad, 
	    copias, 
	    categoria 
	    );
	 // 1. Obtener nº de filas
	 int filas = this.tablaAlumnos.getRowCount();
	 // 2. Agrego a mi array el elemento
	 if (this.indiceEditar == -1) {
	  //Si indice = -1 es porque vamos a registrar un elemento a mi array
	 // this.listaAlumnos.add(alu);
	 } else {
	  //Si indice es diferente es poque estamos editando la información
	  //this.listaAlumnos.set(this.indiceEditar, alu);
	 }
	 if (this.indiceEditar == -1) {
	  // 3. Agrego a mi tabla
	  this.tablaAlumnos.setText(filas, 0, String.valueOf(this.codigo));
	  this.codigo++;
	  this.tablaAlumnos.setText(filas, 1, alu.toString());
	  this.tablaAlumnos.setText(filas, 2, facultad);
	  this.tablaAlumnos.setText(filas, 3, escuela);
	  this.tablaAlumnos.setText(filas, 4, categoria);
	  //this.tablaAlumnos.setText(filas, 5, String.valueOf(monto));
	  //En el constructor se pone la ubicación de la imagen que esta
	  //en la carpeta "images"
	  PushButton btnEliminar = new PushButton("Cancelar");
	  //El tamaño del boton
	  btnEliminar.setWidth("50px");
	  btnEliminar.setHeight("16px");
	  btnEliminar.addClickHandler(new ClickHandler() {
	   //Agregamos el evento clic al boton eliminar, que eliminara un elemento
	   //de la tabla
	   @Override
	   public void onClick(ClickEvent event) {
	   // int filaDel = listaAlumnos.indexOf(alu);
	    //listaAlumnos.remove(filaDel);
	   // tablaAlumnos.removeRow(filaDel + 1);
	   }
	  });
	  this.tablaAlumnos.setWidget(filas, 5, btnEliminar);
	  PushButton btnEditar = new PushButton("Cancelar");
	  btnEditar.setWidth("50px");
	  btnEditar.setHeight("16px");
	  btnEditar.addClickHandler(new ClickHandler() {
	   //Agregamos el evento clic al boton modificar, que mostrara los datos
	   //del alumno seleccionado en las cajas de texto y combos
	   @Override
	   public void onClick(ClickEvent event) {
		//   int filaDel = listaAlumnos.indexOf(alu);
		  //  listaAlumnos.remove(filaDel);
		 //   tablaAlumnos.removeRow(filaDel + 1);
		   
	    /*indiceEditar=listaAlumnos.indexOf(alu);
	    Alumno aluTemp = listaAlumnos.get(indiceEditar);
	    txtTitulo.setText(aluTemp.getNombre());
	    txtAutor.setText(aluTemp.getApellido());
	    seleccionarFacultad(aluTemp.getFacultad());
	    seleccionarEscuela(aluTemp.getEscuela());
	    if (aluTemp.getCategoria().equals(rbtA.getText())) {
	     rbtA.setValue(true);
	    } else if (aluTemp.getCategoria().equals(rbtB.getText())) {
	     rbtB.setValue(true);
	    } else {
	     rbtC.setValue(true);
	    }
	   */}
	  });
	  //this.tablaAlumnos.setWidget(filas, 5, btnEditar);
	  // 4. Formatos, agregamos formatos css, que se encuentra
	  // En el archivo css
	  this.tablaAlumnos.getCellFormatter().addStyleName(filas, 
	        5,"columnaNumerica");
	  this.tablaAlumnos.getCellFormatter().addStyleName(filas, 
	        6,"columnaBoton");
	  this.tablaAlumnos.getCellFormatter().addStyleName(filas, 
	        7,"columnaBoton");
	 }else{
	  //Estamos editando la información del alumno o actualizando la 
	  //información del mismo
	  this.tablaAlumnos.setText(this.indiceEditar+1, 1, alu.toString());
	  this.tablaAlumnos.setText(this.indiceEditar+1, 2, facultad);
	  this.tablaAlumnos.setText(this.indiceEditar+1, 3, escuela);
	  this.tablaAlumnos.setText(this.indiceEditar+1, 4, categoria);
	  //this.tablaAlumnos.setText(this.indiceEditar+1, 5, String.valueOf(monto));
	 }
	 this.limpiar();
	}




	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		//btnAgregar.setVisible(false);
				
		 // Fila del Nombre
		 this.gridPrincipal.setWidget(0, 0, this.lblTitulo);
		 this.gridPrincipal.setWidget(0, 1, this.txtTitulo);
		 // Fila del Apellido
		 this.gridPrincipal.setWidget(1, 0, this.lblAutor);
		 this.gridPrincipal.setWidget(1, 1, this.txtAutor);
		 // Fila de la Facultad
		 this.gridPrincipal.setWidget(2, 0, this.lblEditorial);
		 this.gridPrincipal.setWidget(2, 1, this.cboEditorial);
		 this.llenarFacultades();
		 this.cboEditorial.addChangeHandler(new ChangeHandler() {
		  public void onChange(ChangeEvent event) {
		    llenarEscuelas();
		  }
		 });
		 this.cboEditorial.setSelectedIndex(0);
		 // Fila de la Escuela
		 this.gridPrincipal.setWidget(3, 0, this.lblCopias);
		 this.gridPrincipal.setWidget(3, 1, this.cboCopias);
		 this.llenarEscuelas();
		 // Fila de la Categoria
		 this.gridPrincipal.setWidget(4, 0, this.lblEstado);
		 Grid gridCategoria = new Grid(1, 3);
		 gridCategoria.setWidget(0, 0, this.rbtA);
		 gridCategoria.setWidget(0, 1, this.rbtB);
		 gridCategoria.setWidget(0, 2, this.rbtC);
		 this.gridPrincipal.setWidget(4, 1, gridCategoria);
		 // Seleccionar la categoria A por defecto
		 this.rbtA.setValue(true);
		 // Fila del Boton
		 this.gridPrincipal.setWidget(5, 0, this.btnAgregar);
		 this.gridPrincipal.setWidget(5, 1, this.lblTiempo);
		 this.lblTiempo.setText(new java.util.Date().toString());
		 // Agregando el Grid
		 this.principal.add(gridPrincipal);
		 // Creando la tabla
		 // Estableciendo el nombre a las columnas
		 this.tablaAlumnos.setText(0, 0, "Codigo"); //codigo
		 this.tablaAlumnos.setText(0, 1, "Titulo , Autor"); //alumno
		 this.tablaAlumnos.setText(0, 2, "Editorial");//facultad
		 this.tablaAlumnos.setText(0, 3, "Copias");//escuela
		 this.tablaAlumnos.setText(0, 4, "Estado");//categoria
		// this.tablaAlumnos.setText(0, 5, "Solicitar");//monto
		 this.tablaAlumnos.setText(0, 5, "Cancelar Solicitud");//eliminar
		 //this.tablaAlumnos.setText(0, 7, "Editar");//editar
		 this.tablaAlumnos.addStyleName("estiloTabla");
		 // Agregando los estilos a las columnas
		 this.tablaAlumnos.getRowFormatter().addStyleName(0, "cabezeraTabla");
		 this.tablaAlumnos.getCellFormatter().addStyleName(0, 5,"columnaNumerica");
		 // Agregando la tabla de alumnos
		 this.principal.add(this.tablaAlumnos);
		 // Asociando nuestro codigo con nuestro Html
		 RootPanel.get("principal").add(principal);
		 // Creamos el evento click del boton
		 this.btnAgregar.addClickHandler(new ClickHandler() {
		  @Override
		  public void onClick(ClickEvent event) {
		   // Nombre del envento a desencadenar
		   agregarAlumno();
		  }
		 });
		 //Actualizar hora
		 //Creando un timer para simular cambios 
		 Timer timer=new Timer() {
		  @Override
		  public void run() {
		    //Va a llamar un metodo
		    lblTiempo.setText(new java.util.Date().toString());
		  }
		 };
		 //Cada 1000 ms se llamara al metodo run del Timer
		 timer.scheduleRepeating(1000);
		 
		final Button sendButton = new Button("Buscar libro");
		final TextBox nameField = new TextBox();
		nameField.setText("");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Busqueda");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Titulo:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Indice y Titulo:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(true);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}
		
		// TODO #08: create login controls
				sendButton.setEnabled(true);		
				nameField.setEnabled(true);	

				signInLink.getElement().setClassName("login-area");
				signInLink.setTitle("sign out");
				loginImage.getElement().setClassName("login-area");
				loginPanel.add(signInLink);
				RootPanel.get("loginPanelContainer").add(loginPanel);
				final StringBuilder userEmail = new StringBuilder();
				greetingService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
					@Override
					public void onFailure(final Throwable caught) {
						GWT.log("login -> onFailure");
					}

					@Override
					public void onSuccess(final LoginInfo result) {
						if (result.getName() != null && !result.getName().isEmpty()) {
							addGoogleAuthHelper();
							loadLogout(result);
							sendButton.setEnabled(true);	
							nameField.setEnabled(true);
						} else {
							loadLogin(result);
						}
						userEmail.append(result.getEmailAddress());
					}
				});
				// TODO #08:> end

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
}
