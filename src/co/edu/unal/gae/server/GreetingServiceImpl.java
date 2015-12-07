package co.edu.unal.gae.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import co.edu.unal.gae.client.GreetingService;
import co.edu.unal.gae.shared.FieldVerifier;
import co.edu.unal.gae.shared.LoginInfo;

//import co.edu.unal.gae.shared.many2many.ofy.MusicFestival;

import co.edu.unal.gae.shared.ofytable.OfyTable;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.LoadResult;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings({"serial","unused"})
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService
{
	private static Logger log = Logger.getLogger(GreetingServiceImpl.class.getCanonicalName());
	
	@Override
	public void init( ServletConfig sc )
	{
		try
		{
			super.init( sc );
			
		//	ObjectifyService.register( Musician.class );
		//	ObjectifyService.register( Album.class );
		//	ObjectifyService.register( Band.class );
		//	ObjectifyService.register( DanceBand.class );
		//	ObjectifyService.register( RockBand.class );
		//	ObjectifyService.register( MusicFestival.class );
			
			ObjectifyService.register( OfyTable.class );
			
			ofy = ObjectifyService.factory().begin();
		}
		catch ( ServletException e )
		{
			e.printStackTrace();
		}
	}

	public String greetServer( String input ) throws IllegalArgumentException
	{
		// Verify that the input is valid. 
		if ( !FieldVerifier.isValidName( input ) )
		{
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException( "Name must be at least 4 characters long" );
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader( "User-Agent" );


		input = escapeHtml( input );
		userAgent = escapeHtml( userAgent );
		//String name = "Keith Jarrett Trio";
		//Key<RockBand> key = storeBand();
	   // Band band = loadBand( name );
//		
		//return "Stored=" + name + "<br/>Loaded=" + band + "<br/><br/><br/><br/>";

//		String name = "ETC 2015";
//		Key<MusicFestival> key = storeFestival(name);
//	    MusicFestival mf = loadFestival(name);
//		
//		return "Stored=" + name + "<br/>Loaded=" + mf + "<br/><br/><br/><br/>";
		
		Key<OfyTable> key = storeBook();
	    OfyTable m = loadBook( 1 );

		return "Codigo=" + key + "<br/>Titulo=" + m + "<br/><br/><br/><br/>";

		//Key<Musician> key = storeMusician();
		//Musician m = loadMusician( "Keith Jarrett" );

		//return "Stored=" + key + "<br/>Loaded=" + m + "<br/><br/><br/><br/>";
	}

	
	protected Key<OfyTable> storeBook()
	{
		OfyTable b1 = new OfyTable( 1, "Memoria por Correspondencia", "Emma Reyes", "eLibros",2,"Bueno" );
		OfyTable b2 = new OfyTable( 2, "Sin tetas no hay paraíso", "Gustavo Bolívar", "Random House Mondadori",4,"Bueno" );
		OfyTable b3 = new OfyTable( 3, "El país de la canela", "William Ospina", "Random House Mondadori",1,"Regular" );
		OfyTable b4 = new OfyTable( 4, "Víctor Carranza, alias el patrón", "Iván Cepeda y Javier Giraldo", "Random House Mondadori",2,"Bueno" );
		OfyTable b5 = new OfyTable( 5, "Las reputaciones", "Juan Gabriel Vásquez", "Alfaguara",2,"Bueno" );
		OfyTable b6 = new OfyTable( 6, "Peroratas", "Fernando Vallejo", "Alfaguara",2,"Bueno" );		
		return ofy.save().entity( b1 ).now();
	}  

	protected OfyTable loadBook( long id )
	{
		LoadResult<OfyTable> r = ofy.load().type( OfyTable.class ).id( id );
		
		return r.now();
	}
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml( String html )
	{
		return (html != null ? html.replaceAll( "&", "&amp;" ).replaceAll( "<", "&lt;" ).replaceAll( ">", "&gt;" ) : null);
	}
	// TODO #11: implement login helper methods in service implementation	

		@Override
		public String getUserEmail(final String token) {
			final UserService userService = UserServiceFactory.getUserService();
			final User user = userService.getCurrentUser();
			if (null != user) {
				return user.getEmail();
			} else {
				return "noreply@sample.com";
			}
		}

		@Override
		public LoginInfo login(final String requestUri) {
			final UserService userService = UserServiceFactory.getUserService();
			final User user = userService.getCurrentUser();
			final LoginInfo loginInfo = new LoginInfo();
			if (user != null) {
				loginInfo.setLoggedIn(true);
				loginInfo.setName(user.getEmail());
				loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
			} else {
				loginInfo.setLoggedIn(false);
				loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
			}
			return loginInfo;
		}

		@Override
		public LoginInfo loginDetails(final String token) {
			String url = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + token;

			final StringBuffer r = new StringBuffer();
			try {
				final URL u = new URL(url);
				final URLConnection uc = u.openConnection();
				final int end = 1000;
				InputStreamReader isr = null;
				BufferedReader br = null;
				try {
					isr = new InputStreamReader(uc.getInputStream());
					br = new BufferedReader(isr);
					final int chk = 0;
					while ((url = br.readLine()) != null) {
						if ((chk >= 0) && ((chk < end))) {
							r.append(url).append('\n');
						}
					}
				} catch (final java.net.ConnectException cex) {
					r.append(cex.getMessage());
				} catch (final Exception ex) {
					log.log(Level.SEVERE, ex.getMessage());
				} finally {
					try {
						br.close();
					} catch (final Exception ex) {
						log.log(Level.SEVERE, ex.getMessage());
					}
				}
			} catch (final Exception e) {
				log.log(Level.SEVERE, e.getMessage());
			}

			final LoginInfo loginInfo = new LoginInfo();
			try {
				final JsonFactory f = new JsonFactory();
				JsonParser jp;
				jp = f.createJsonParser(r.toString());
				jp.nextToken();
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					final String fieldname = jp.getCurrentName();
					jp.nextToken();
					if ("picture".equals(fieldname)) {
						loginInfo.setPictureUrl(jp.getText());
					} else if ("name".equals(fieldname)) {
						loginInfo.setName(jp.getText());
					} else if ("email".equals(fieldname)) {
						loginInfo.setEmailAddress(jp.getText());
					}
				}
			} catch (final JsonParseException e) {
				log.log(Level.SEVERE, e.getMessage());
			} catch (final IOException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
			return loginInfo;
		}

		// TODO #11:> end

/*	protected Key<Musician> storeMusician()
	{
		Musician m = createMusician( "Keith Jarrett", "Mar 24, 1945", 7 );

		return ofy.save().entity( m ).now();
	}  

	protected Musician loadMusician( String id )
	{
		LoadResult<Musician> r = ofy.load().type( Musician.class ).id( id );
		
		return r.now();
	}

	protected static Musician createMusician( String name, String birthday, int number )
	{
		final DateFormat df = new SimpleDateFormat( "MMM d, yyyy" );

		Musician m = new Musician( name );
		try
		{
			m.birthday = df.parse( birthday );
		}
		catch ( ParseException e )
		{
			e.printStackTrace();
		}
		m.favouriteNumber = number;

		return m;
	}
	protected Key<MusicFestival> storeFestival(final String name)
	{
		MusicFestival band = createFestival("ETC 2015");
		return ofy.save().entity( band ).now();
	}

	protected MusicFestival loadFestival( String name )
	{
		LoadResult<MusicFestival> r = ofy.load().type( MusicFestival.class ).id( name );
		
		return r.now();
	}
	

	protected Key<Band> storeRockBand()
	{
		Band band = createRockBand("Keith Jarrett");


		return ofy.save().entity( band ).now();
	}
	protected Key<Band> storeDanceBand()
	{
		Band band = createDanceBand("Alejandro Martínez");


		return ofy.save().entity( band ).now();
	}

	protected Band loadBand( String name )
	{
		LoadResult<RockBand> r = ofy.load().type( RockBand.class ).id( name );
		
		return r.now();
	}
	
	
	protected RockBand createRockBand (String name){
		
		RockBand band = new RockBand();
		band.name = name;
		
		band.members.add( createMusician( "Keith Jarrett", "Mar 24, 1945", 7 ) );
		band.members.add( createMusician( "Jack Dejonette", "Abr 24, 1955", 8 ) );
		band.members.add( createMusician( "Charlie Haden", "Jun 24, 1965", 9 ) );
		
		return band;
	}
	protected DanceBand createDanceBand (String name){
		
		DanceBand band = new DanceBand();
		band.name = name;
		
		band.members.add( createMusician( "Alejandro Martínez", "Mar 24, 1945", 7 ) );
		band.members.add( createMusician( "Manuel Hurtado", "Abr 24, 1955", 8 ) );
		band.members.add( createMusician( "Michael Obama", "Jun 24, 1965", 9 ) );
		
		return band;
	}
	protected MusicFestival createFestival (final String name){
		MusicFestival mf = new MusicFestival();
		
		mf.name = name;
		
		RockBand rband = createRockBand("Rock Band"); 
		DanceBand dband = createDanceBand("Dance band");
		
		mf.bands.add(dband);
		mf.bands.add(rband);
				
		return mf;
	}

	protected Key<Band> storeBand()
	{
		Band band = new RockBand();
		band.name = "Keith Jarrett Trio";
		band.members.add( createMusician( "Keith Jarrett", "Mar 24, 1945", 7 ) );


		return ofy.save().entity( band ).now();
	}*/
	

	
	private Objectify ofy;
}
