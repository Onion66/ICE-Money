package id.ac.umn.icemoney.view.home;

//import id.ac.umn.icemoney.repository.TransactionRepository;

//public class HomeViewModel extends AndroidViewModel {
//    TransactionRepository transactionRepository;
//    LiveData<List<Transaction>> transactionList;
//
//    public HomeViewModel(@NonNull Application application) {
//        super(application);
//        transactionRepository = new TransactionRepository(application);
//        transactionList = transactionRepository.getTransactionList();
//    }
//    public LiveData<List<Transaction>> getTransactionList(){
//        return this.transactionList;
//    }
//    public void insert(Transaction transaction) {
//        transactionRepository.insert(transaction);
//    }
//    public void deleteAll() {
//        transactionRepository.deleteAll();
//    }
//    public void delete(Transaction transaction) {
//        transactionRepository.delete(transaction);
//    }
//    public void update(Transaction transaction) {
//        transactionRepository.update(transaction);
//    }
//}
