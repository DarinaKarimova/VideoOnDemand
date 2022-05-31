package exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int status;
    private Date date;
    private String message;
    private String description;

//    public ErrorMessage(int status, Date date, String message, String description){
//
//    }
}
