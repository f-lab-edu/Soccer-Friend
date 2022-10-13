package utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpStatusCode {
    public final static ResponseEntity<Void> OK = new ResponseEntity<Void>(HttpStatus.OK);
    public final static ResponseEntity<Void> BAD_REQUEST = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    public final static ResponseEntity<Void> CONFLICT = new ResponseEntity<Void>(HttpStatus.CONFLICT);
}