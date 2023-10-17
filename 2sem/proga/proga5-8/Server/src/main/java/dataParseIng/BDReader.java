package dataParseIng;

import bdMng.BdMng;
import org.postgresql.geometric.PGpoint;
import сlasses.Address;
import сlasses.Coordinates;
import сlasses.Organization;
import сlasses.OrganizationType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;

public class BDReader {
    public HashSet<Organization> getAllData() throws IOException, SQLException {
        HashSet<Organization> mySet = new HashSet<>();
        BdMng bd = new BdMng();
        Connection cnt = bd.cnt();
        ResultSet res = bd.giveResOfQuery(cnt, "SELECT * FROM dblab");
        while (res.next()){
            int id = res.getInt("id");
            String name = res.getString("name");
            PGpoint coordinates = (PGpoint) res.getObject("coordinates");
            float x = (float) coordinates.x;
            float y = (float) coordinates.y;
            Timestamp crdate = res.getTimestamp("creationdate");
            LocalDateTime dda = crdate.toLocalDateTime();
            Float anl = res.getFloat("annualturnover");
            OrganizationType tp = OrganizationType.valueOf(res.getString("type"));
            String psAdd = res.getString("postaladdressstreet");
            String zipCd = res.getString("postaladdresszipcoder");
            int userId = res.getInt("userid");
            Organization tmp = new Organization(id, name, new Coordinates((long) x,y), dda, anl, tp, new Address(psAdd, zipCd), userId);
            mySet.add(tmp);
        }


        return mySet;
    }
}

/*
CREATE TABLE dblab (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinates POINT NOT NULL,
    creationdate TIMESTAMP NOT NULL,
    annualturnover FLOAT,
    type VARCHAR(50) NOT NULL,
    postaladdressstreet VARCHAR(255),
    postaladdresszipcoder VARCHAR(20),
    userid INT NOT NULL
);

 */


/*
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

*/