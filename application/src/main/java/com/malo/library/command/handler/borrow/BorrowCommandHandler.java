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
import com.malo.library.exception.business.BusinessException;
import com.malo.library.exception.business.BusinessExceptionKey;
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
    public void handle(BorrowCommand command, HandlingContext handlingContext) throws BusinessException {

        Member member = this.memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionKey.MEMBER_NOT_FOUND, command.getMemberId()));

        if (member.isNotActive()) {
            throw new BusinessException(BusinessExceptionKey.MEMBER_IS_INACTIVE, member.getId());
        }

        if (member.isBlocked()) {
            throw new BusinessException(BusinessExceptionKey.MEMBER_IS_BLOCKED, member.getId());
        }

        Book book = this.bookRepository.findById(command.getBookId());

        if (book.notFound()){
          throw  new BusinessException(BusinessExceptionKey.BOOK_NOT_FOUND, command.getBookId())  ;
        }

        if (book.notAvailable()) {
            throw new BusinessException(BusinessExceptionKey.BOOK_NOT_AVAILABLE, book.getId());
        }

        if (this.borrowingRepository.isBookCurrentlyBorrowedByMember(book.getId(), member.getId())) {
            throw new BusinessException(BusinessExceptionKey.BOOK_ALREADY_BORROWED_BY_MEMBER, book.getId(), member.getId());
        }

        if (this.borrowingRepository.countOnGoingForMember(command.getMemberId()) >= MAX_BORROW_BY_MEMBER) {
            throw new BusinessException(BusinessExceptionKey.MAX_BORROW_LIMIT_REACHED,MAX_BORROW_BY_MEMBER, command.getMemberId());
        }

        if (!this.bookRepository.borrowOne(book.getId())) {
            throw new BusinessException(BusinessExceptionKey.BOOK_BORROW_FAILED, book.getId());
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

     Borrowing savedBorrowing = borrowingRepository.save(borrowing);
     command.setId(savedBorrowing.getId());
    }

    public void init() {
        this.memberRepository.save(Member.builder().id(1L).status(MemberAccountStatus.ACTIVE).build());
    }
}
