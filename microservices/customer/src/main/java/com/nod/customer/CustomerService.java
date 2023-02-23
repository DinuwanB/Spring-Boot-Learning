package com.nod.customer;


import com.nod.clients.frauds.FraudCheckResponse;
import com.nod.clients.frauds.FraudClient;
import com.nod.clients.notifications.NotificationClient;
import com.nod.clients.notifications.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
    public void registerCustomer(CustomerRegistrationRequest request ) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //Store customer in db
        customerRepository.saveAndFlush(customer);

        //Check if fraudster using client service
        FraudCheckResponse fraudCheckRes = fraudClient.isFraudster(customer.getId());

        if(fraudCheckRes.isFraudster()){
            throw new IllegalStateException("fraudster");
        }
        //make it async -> add to queue
        notificationClient.sendNotification(new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, Welcome to NOD YT Channel...",
                        customer.getFirstName())));

    };

}
