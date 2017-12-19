/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.sql.SQLException;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface MySessionRemote 
{

    void post(String key, String value) throws SQLException;
    
}
