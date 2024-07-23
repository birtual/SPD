
package lopicost.spd.security.helper;

import lopicost.config.logger.Logger;
import lopicost.spd.model.Usuario;

import lopicost.spd.persistence.UsuarioDAO;

import lopicost.spd.utils.SPDConstants;

public class LoginHelper {
	
	
	public boolean chkUser(String userId, String password) throws Exception{
		boolean userOK = false;

		// Obtiene el usuario 
		Usuario user=null;	
		UsuarioDAO dao= new  UsuarioDAO();
		user = dao.findByIdUser(userId);
		
		if (user==null){
			Logger.log(SPDConstants.LOG_ID, "Usuario incorrecto", Logger.DEBUG);
			return userOK;
		}
		// Verifica el password 
		//if (!PasswordUtil.pass_Validate(password, user.getHashPass())){
		//Verificación sencilla, sin Hash
		if (!password.equals(user.getHashPass())){
			Logger.log(SPDConstants.LOG_ID, "password incorrecto", Logger.DEBUG);
			return userOK;
		}
		userOK=true;
		return userOK;
	}

	


}
