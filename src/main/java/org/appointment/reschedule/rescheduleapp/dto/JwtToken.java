package org.appointment.reschedule.rescheduleapp.dto;
import java.io.Serializable;
public class JwtToken  implements Serializable{
	
    private static final long serialVersionUID = 1L;
    private String token;
    private long iat;
    
public JwtToken() {
    // TODO Auto-generated constructor stub
}
public String getToken() {
    return token;
}
public void setToken(String token) {
    this.token = token;
}
public long getIat() {
    return iat;
}
public void setIat(long iat) {
    this.iat = iat;
}
}
