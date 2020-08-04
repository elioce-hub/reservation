package hrs;

import hrs.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationinquiryViewHandler {


    @Autowired
    private ReservationinquiryRepository reservationinquiryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserved_then_CREATE_1 (@Payload Reserved reserved) {
        try {
            if (reserved.isMe()) {
                // view 객체 생성
                Reservationinquiry reservationinquiry = new Reservationinquiry();
                // view 객체에 이벤트의 Value 를 set 함
                reservationinquiry.setCustomerName(reserved.getCustomerName());
                reservationinquiry.setNights(reserved.getNights());
                reservationinquiry.setCheckinDate(reserved.getCheckinDate());
                reservationinquiry.setStatus(reserved.getStatus());
                reservationinquiry.setPrice(reserved.getPrice());
                reservationinquiry.setHotelId(reserved.getHotelId());
                reservationinquiry.setId(reserved.getId());
                // view 레파지 토리에 save
                reservationinquiryRepository.save(reservationinquiry);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationConfirmed_then_UPDATE_1(@Payload ReservationConfirmed reservationConfirmed) {
        try {
            if (reservationConfirmed.isMe()) {
                // view 객체 조회
                Optional<Reservationinquiry> reservationinquiryOptional = reservationinquiryRepository.findById(reservationConfirmed.getReservationId());
                if( reservationinquiryOptional.isPresent()) {
                    Reservationinquiry reservationinquiry = reservationinquiryOptional.get();
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    reservationinquiry.setStatus(reservationConfirmed.getStatus());
                    // view 레파지 토리에 save
                    reservationinquiryRepository.save(reservationinquiry);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationConfirmedCanceled_then_UPDATE_2(@Payload ReservationConfirmedCanceled reservationConfirmedCanceled) {
        try {
            if (reservationConfirmedCanceled.isMe()) {
                // view 객체 조회
                Optional<Reservationinquiry> reservationinquiryOptional = reservationinquiryRepository.findById(reservationConfirmedCanceled.getReservationId());
                if( reservationinquiryOptional.isPresent()) {
                    Reservationinquiry reservationinquiry = reservationinquiryOptional.get();
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    reservationinquiry.setStatus(reservationConfirmedCanceled.getStatus());
                    // view 레파지 토리에 save
                    reservationinquiryRepository.save(reservationinquiry);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationCanceled_then_DELETE_1(@Payload ReservationCanceled reservationCanceled) {
        try {
            if (reservationCanceled.isMe()) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}