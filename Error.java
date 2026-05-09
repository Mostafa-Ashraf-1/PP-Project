public class Error {

    public int errors = 0;

    public Error(int errors)
    {
        this.errors = errors;
    }
    public void inc()
    {
        errors++;
    }

    public int get()
    {
        return errors;
    }
}
