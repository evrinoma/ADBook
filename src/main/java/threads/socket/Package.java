package threads.socket;

public class Package {

    public static final String TYPE_USER = "user";
    public static final String TYPE_CMD = "cmd";
    public static final String TYPE_EMPTY = "empty";
    public static final String TYPE_EXIT = "exit";
    public static final String TYPE_EXPAND = "expand";
    public static final String TYPE_CONNECT = "connect";
    public static final String TYPE_FLUSH = "flush";

    protected String query;
    protected String message = TYPE_EMPTY;
    protected String type = TYPE_EMPTY;

    public Package(String query) {
        this.query = query;
        this.toParse();
    }

    public Package(String message, String type) {
        this.message = message;
        this.type = type;
        this.toQuery();
    }

    public String getQuery() {
        return query;
    }

    public Package setQuery(String query) {
        this.query = query;

        return this;
    }

    public String getMessage() {
        return message;
    }

    public Package setMessage(String message) {
        this.message = message;

        return this;
    }

    public String getType() {
        return type;
    }

    public Package setType(String type) {
        this.type = type;

        return this;
    }

    public boolean isEmpty()
    {
        return this.query == null;
    }

    public boolean isFlush()
    {
        return this.type == TYPE_FLUSH;
    }

    protected void toQuery()
    {
        switch (this.type) {
            case TYPE_USER:
                this.setQuery(TYPE_USER +"["+ message +"]");
                break;
            case TYPE_CMD :
                this.setQuery(TYPE_CMD+"["+ message +"]");
                break;
            case TYPE_EXPAND:
                this.setQuery(TYPE_EXPAND+"["+ message +"]");
                break;
            case TYPE_FLUSH:
                this.setQuery(TYPE_FLUSH+"["+ TYPE_FLUSH +"]");
                break;
            default:
                this.setQuery(TYPE_EMPTY+"["+ TYPE_EMPTY +"]");
        }
    }

    protected void toParse()
    {
        if (this.query.startsWith(TYPE_CMD+"[")  && this.query.contains("]")) {
            this.setMessage(this.query.subSequence((TYPE_CMD+"[").length(), this.query.lastIndexOf("]")).toString());
            this.setType( TYPE_CMD);
        }

        if (this.query.startsWith(TYPE_EXIT+"[")  && this.query.contains("]")) {
            this.setMessage(this.query.subSequence((TYPE_CMD+"[").length(), this.query.lastIndexOf("]")).toString());
            this.setType( TYPE_EXIT);
        }

        if (this.query.startsWith(TYPE_CONNECT+"[")  && this.query.contains("]")) {
            this.setMessage( this.query.subSequence((TYPE_CONNECT+"[").length(), this.query.lastIndexOf("]")).toString());
            this.setType( TYPE_CONNECT);
        }

        if (this.query.startsWith(TYPE_FLUSH+"[")  && this.query.contains("]")) {
            this.setMessage( this.query.subSequence((TYPE_FLUSH+"[").length(), this.query.lastIndexOf("]")).toString());
            this.setType( TYPE_FLUSH);
        }

        if (this.query.startsWith(TYPE_USER+"[")  && this.query.contains("]")) {
            this.setMessage(this.query.subSequence((TYPE_USER+"[").length(), this.query.lastIndexOf("]")).toString());
            this.setType( TYPE_USER);
        }

        if (this.query.startsWith(TYPE_EXPAND+"[")  && this.query.contains("]")) {
            this.setMessage(this.query.subSequence((TYPE_EXPAND+"[").length(), this.query.lastIndexOf("]")).toString());
            this.setType( TYPE_EXPAND);
        }
    }
}
