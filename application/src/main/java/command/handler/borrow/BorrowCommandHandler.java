package command.handler.borrow;

import command.command.borrow.BorrowCommand;
import command.handler.CommandHandler;
import domain.model.entities.Book;
import domain.model.entities.Borrowing;
import domain.model.entities.Member;
import domain.model.valueObject.BorrowStatus;
import domain.model.valueObject.MemberAccountStatus;
import domain.model.valueObject.ReturnStatus;
import domain.repository.BookRepository;
import domain.repository.BorrowingRepository;
import domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowCommandHandler implements CommandHandler<BorrowCommand> {
    final static Short MAX_BORROW_BY_MEMBER = 5; //to move to borrowReference common
    BorrowingRepository borrowingRepository;

    public BorrowCommandHandler(BorrowingRepository borrowingRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.init();
    }

    BookRepository bookRepository;
    MemberRepository memberRepository;

    @Override
    public void handle(BorrowCommand command) {
        Member member = this.memberRepository.findById(command.getMemberId()).orElseThrow(() -> new RuntimeException("member not found "));

        if (member.isNotActive()) {
            throw new RuntimeException(" member is inactive");
        }

        if (member.isBlocked()) {
            throw new RuntimeException(" member  is blocked");
        }

        Book book = this.bookRepository.findById(command.getBookId());

        if(book.notFound()){
            throw new RuntimeException(" book is not found");
        }

        if(book.notAvailable()){
            throw new RuntimeException(" book is not available");
        }

        if(this.borrowingRepository.countOnGoingForMember(command.getMemberId()) >= MAX_BORROW_BY_MEMBER){
            throw new RuntimeException("you are not allowed to borrow  than "+MAX_BORROW_BY_MEMBER+" book");
        }

        Borrowing borrowing = Borrowing.builder()
                .memberId(command.getMemberId())
                .bookId(command.getBookId())
                .pickUpDate(command.getPickUpDate())
                .returnedDate(command.getReturnedDate())
                .borrowStatus(BorrowStatus.ONGOING)
                .returnStatus(ReturnStatus.ONTIME)
                .build();

       borrowingRepository.save(borrowing);
    }

    public void init(){
        this.memberRepository.save(Member.builder().id(1L).status(MemberAccountStatus.ACTIVE).build());
    }
}
