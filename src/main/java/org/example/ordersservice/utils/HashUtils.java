package org.example.ordersservice.utils;

import org.example.ordersservice.exception.OrderServiceException;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HashUtils {

    private final Hashids hashids;


    public HashUtils(@Value("${hash.salt}") String salt,
                     @Value("${hash.min-hash-length}") int minHashLength) {
        this.hashids = new Hashids(salt, minHashLength);
    }

    public String hashOf(Long id) {
        return hashids.encode(id);
    }


    public Long idOf(String hash) {
        long[] res = hashids.decode(hash);
        if (res != null && res.length > 0) {
            return res[0];
        }
        var msg = "Failed to decode hashed id from string: %s".formatted(hash);
        throw new OrderServiceException(msg, HttpStatus.BAD_REQUEST);
    }

}
