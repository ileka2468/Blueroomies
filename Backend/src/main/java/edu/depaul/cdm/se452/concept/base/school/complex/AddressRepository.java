package edu.depaul.cdm.se452.concept.base.school.complex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import edu.depaul.cdm.se452.concept.Repository;

public class AddressRepository implements Repository<Address, Long> {
    private static HashMap<Long, Address> ADDRESSES = new HashMap<Long, Address>();

    @Override
    public Address save(Address course) {
        ADDRESSES.put(course.getId(), course);
        return course;
    }    

    @Override
    public Optional<Address> findById(Long id) {
        Optional<Address> address = ADDRESSES.entrySet().stream()
            .filter(e -> id.equals(e.getKey()))
            .map(Map.Entry::getValue)
            .findFirst();

        return address;
    }

    @Override
    public int count() {
        return ADDRESSES.size();
    }    


    public Address findByName(String name) {
        Address retval = null;
        for(Address address : ADDRESSES.values()) {
            if (address.getLocation().equalsIgnoreCase(name)) {
                retval = address;
            }
        }
        return retval;
    }}