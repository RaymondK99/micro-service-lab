/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package se.raykal.msdemo.model;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class MsDemoMessage extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -684323569456796344L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"MsDemoMessage\",\"namespace\":\"se.raykal.msdemo.model\",\"fields\":[{\"name\":\"message\",\"type\":\"string\",\"doc\":\"Id of the order filed\"},{\"name\":\"customer_id\",\"type\":\"string\",\"doc\":\"Id of the customer\"},{\"name\":\"number\",\"type\":\"int\",\"doc\":\"Number\"}],\"version\":\"1\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<MsDemoMessage> ENCODER =
      new BinaryMessageEncoder<MsDemoMessage>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<MsDemoMessage> DECODER =
      new BinaryMessageDecoder<MsDemoMessage>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<MsDemoMessage> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<MsDemoMessage> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<MsDemoMessage>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this MsDemoMessage to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a MsDemoMessage from a ByteBuffer. */
  public static MsDemoMessage fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  /** Id of the order filed */
  @Deprecated public java.lang.CharSequence message;
  /** Id of the customer */
  @Deprecated public java.lang.CharSequence customer_id;
  /** Number */
  @Deprecated public int number;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public MsDemoMessage() {}

  /**
   * All-args constructor.
   * @param message Id of the order filed
   * @param customer_id Id of the customer
   * @param number Number
   */
  public MsDemoMessage(java.lang.CharSequence message, java.lang.CharSequence customer_id, java.lang.Integer number) {
    this.message = message;
    this.customer_id = customer_id;
    this.number = number;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return message;
    case 1: return customer_id;
    case 2: return number;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: message = (java.lang.CharSequence)value$; break;
    case 1: customer_id = (java.lang.CharSequence)value$; break;
    case 2: number = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'message' field.
   * @return Id of the order filed
   */
  public java.lang.CharSequence getMessage() {
    return message;
  }

  /**
   * Sets the value of the 'message' field.
   * Id of the order filed
   * @param value the value to set.
   */
  public void setMessage(java.lang.CharSequence value) {
    this.message = value;
  }

  /**
   * Gets the value of the 'customer_id' field.
   * @return Id of the customer
   */
  public java.lang.CharSequence getCustomerId() {
    return customer_id;
  }

  /**
   * Sets the value of the 'customer_id' field.
   * Id of the customer
   * @param value the value to set.
   */
  public void setCustomerId(java.lang.CharSequence value) {
    this.customer_id = value;
  }

  /**
   * Gets the value of the 'number' field.
   * @return Number
   */
  public java.lang.Integer getNumber() {
    return number;
  }

  /**
   * Sets the value of the 'number' field.
   * Number
   * @param value the value to set.
   */
  public void setNumber(java.lang.Integer value) {
    this.number = value;
  }

  /**
   * Creates a new MsDemoMessage RecordBuilder.
   * @return A new MsDemoMessage RecordBuilder
   */
  public static se.raykal.msdemo.model.MsDemoMessage.Builder newBuilder() {
    return new se.raykal.msdemo.model.MsDemoMessage.Builder();
  }

  /**
   * Creates a new MsDemoMessage RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new MsDemoMessage RecordBuilder
   */
  public static se.raykal.msdemo.model.MsDemoMessage.Builder newBuilder(se.raykal.msdemo.model.MsDemoMessage.Builder other) {
    return new se.raykal.msdemo.model.MsDemoMessage.Builder(other);
  }

  /**
   * Creates a new MsDemoMessage RecordBuilder by copying an existing MsDemoMessage instance.
   * @param other The existing instance to copy.
   * @return A new MsDemoMessage RecordBuilder
   */
  public static se.raykal.msdemo.model.MsDemoMessage.Builder newBuilder(se.raykal.msdemo.model.MsDemoMessage other) {
    return new se.raykal.msdemo.model.MsDemoMessage.Builder(other);
  }

  /**
   * RecordBuilder for MsDemoMessage instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<MsDemoMessage>
    implements org.apache.avro.data.RecordBuilder<MsDemoMessage> {

    /** Id of the order filed */
    private java.lang.CharSequence message;
    /** Id of the customer */
    private java.lang.CharSequence customer_id;
    /** Number */
    private int number;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(se.raykal.msdemo.model.MsDemoMessage.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.message)) {
        this.message = data().deepCopy(fields()[0].schema(), other.message);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.customer_id)) {
        this.customer_id = data().deepCopy(fields()[1].schema(), other.customer_id);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.number)) {
        this.number = data().deepCopy(fields()[2].schema(), other.number);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing MsDemoMessage instance
     * @param other The existing instance to copy.
     */
    private Builder(se.raykal.msdemo.model.MsDemoMessage other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.message)) {
        this.message = data().deepCopy(fields()[0].schema(), other.message);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.customer_id)) {
        this.customer_id = data().deepCopy(fields()[1].schema(), other.customer_id);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.number)) {
        this.number = data().deepCopy(fields()[2].schema(), other.number);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'message' field.
      * Id of the order filed
      * @return The value.
      */
    public java.lang.CharSequence getMessage() {
      return message;
    }

    /**
      * Sets the value of the 'message' field.
      * Id of the order filed
      * @param value The value of 'message'.
      * @return This builder.
      */
    public se.raykal.msdemo.model.MsDemoMessage.Builder setMessage(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.message = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'message' field has been set.
      * Id of the order filed
      * @return True if the 'message' field has been set, false otherwise.
      */
    public boolean hasMessage() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'message' field.
      * Id of the order filed
      * @return This builder.
      */
    public se.raykal.msdemo.model.MsDemoMessage.Builder clearMessage() {
      message = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'customer_id' field.
      * Id of the customer
      * @return The value.
      */
    public java.lang.CharSequence getCustomerId() {
      return customer_id;
    }

    /**
      * Sets the value of the 'customer_id' field.
      * Id of the customer
      * @param value The value of 'customer_id'.
      * @return This builder.
      */
    public se.raykal.msdemo.model.MsDemoMessage.Builder setCustomerId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.customer_id = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'customer_id' field has been set.
      * Id of the customer
      * @return True if the 'customer_id' field has been set, false otherwise.
      */
    public boolean hasCustomerId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'customer_id' field.
      * Id of the customer
      * @return This builder.
      */
    public se.raykal.msdemo.model.MsDemoMessage.Builder clearCustomerId() {
      customer_id = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'number' field.
      * Number
      * @return The value.
      */
    public java.lang.Integer getNumber() {
      return number;
    }

    /**
      * Sets the value of the 'number' field.
      * Number
      * @param value The value of 'number'.
      * @return This builder.
      */
    public se.raykal.msdemo.model.MsDemoMessage.Builder setNumber(int value) {
      validate(fields()[2], value);
      this.number = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'number' field has been set.
      * Number
      * @return True if the 'number' field has been set, false otherwise.
      */
    public boolean hasNumber() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'number' field.
      * Number
      * @return This builder.
      */
    public se.raykal.msdemo.model.MsDemoMessage.Builder clearNumber() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MsDemoMessage build() {
      try {
        MsDemoMessage record = new MsDemoMessage();
        record.message = fieldSetFlags()[0] ? this.message : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.customer_id = fieldSetFlags()[1] ? this.customer_id : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.number = fieldSetFlags()[2] ? this.number : (java.lang.Integer) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<MsDemoMessage>
    WRITER$ = (org.apache.avro.io.DatumWriter<MsDemoMessage>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<MsDemoMessage>
    READER$ = (org.apache.avro.io.DatumReader<MsDemoMessage>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
