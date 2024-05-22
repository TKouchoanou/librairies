package com.malo.library.command.handler.borrow;

import com.malo.library.command.command.borrow.BorrowCommand;
import com.malo.library.command.CommandHandler;
import com.malo.library.domain.model.entities.Book;
import com.malo.library.domain.model.entities.Borrowing;
import com.malo.library.domain.model.entities.Member;
import com.malo.library.domain.model.valueObject.BorrowStatus;
import com.malo.library.domain.model.valueObject.MemberAccountStatus;
import com.malo.library.domain.model.valueObject.ReturnStatus;
import com.malo.library.domain.repository.BookRepository;
import com.malo.library.domain.repository.BorrowingRepository;
import com.malo.library.domain.repository.MemberRepository;
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
    public void handle(BorrowCommand command, HandlingContext handlingContext) {

        Member member = this.memberRepository.findById(command.getMemberId()).orElseThrow(() -> new RuntimeException("member not found "));

        if (member.isNotActive()) {
            throw new RuntimeException(" member is inactive");
        }

        if (member.isBlocked()) {
            throw new RuntimeException(" member  is blocked");
        }

        Book book = this.bookRepository.findById(command.getBookId());

        if (book.notFound()) {
            throw new RuntimeException(" book is not found");
        }

        if (book.notAvailable()) {
            throw new RuntimeException(" book is not available");
        }

        if (this.borrowingRepository.isBookCurrentlyBorrowedByMember(book.getId(), member.getId())) {
            throw new RuntimeException("The member has already borrowed this book.");
        }

        if (this.borrowingRepository.countOnGoingForMember(command.getMemberId()) >= MAX_BORROW_BY_MEMBER) {
            throw new RuntimeException("you are not allowed to borrow  than " + MAX_BORROW_BY_MEMBER + " book");
        }

        if(!this.bookRepository.borrowOne(book.getId())){
            throw new RuntimeException("fail to borrow the book");

        }

        handlingContext.doOnFailure(()->this.bookRepository.restoreOne(book.getId()));

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

    public void init() {
        this.memberRepository.save(Member.builder().id(1L).status(MemberAccountStatus.ACTIVE).build());
    }
}
