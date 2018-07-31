package gov.nist.healthcare.cds.service.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author eric
 */
public class CDSiDate  {

  // NOTES for Hossam
  // The baseDate is usually a patient's DOB. The Offset is the amount to add to the date (e.g., 6 months).
  // The default date is for places in the CDSi Logic Specification where no offset exists so we have ot pick a default date.
  // offset comes in the form of a String (e.g., "6 months + 2 weeks - 4 days") and then I parse that string. You might have these fields stored differently
  // This function takes a date and adds to it, but assumes the information is in the order of CDSi rules (years, then months, then weeks, then days).
  // I think this function would yield bad results in one of two ways 1) String Offset is in poor order (e.g. "-4 days + 6 months") 2) String Offset attemtps to substract.
  // For my purposes, I don't need to subtract dates in my logic like you do and all of the CDSi offsets are carefully supplied to be in proper order.
  public static Date calculateDate(Date baseDate, String offset) throws Exception { return calculateDate(baseDate, offset, null); }
  public static Date calculateDate(Date baseDate, String offset, String defaultDate) throws Exception
  {
    // Set the default if no offset is provided
    if (offset == null || offset.isEmpty())
    {
      if(defaultDate == null || defaultDate.isEmpty())
        return null;

      Date date = new SimpleDateFormat("MM/dd/yyyy").parse(defaultDate);
      return(dropTime(date));
    }

    Calendar calOrig = Calendar.getInstance();
    calOrig.setTime(baseDate);

    Calendar cal = Calendar.getInstance();
    cal.setTime(baseDate);

    // Parse the offset string and apply the operations
    StringTokenizer tokens = new StringTokenizer(offset);

    // Take care of the first add, then see if there are more
    int    amount    = Integer.parseInt(tokens.nextToken());
    String field     = tokens.nextToken();
    int    calField = getField(field);

    if(field.equalsIgnoreCase("weeks") || field.equalsIgnoreCase("week"))
      amount = amount*7;

    cal.add(calField, amount);

    // CALCDT-5: If we fell back, but should have went forward, let's do that now
    if(mustRollForward(calOrig, cal, calField))
    {
     cal.add(Calendar.DAY_OF_MONTH, 1);
    }

    while(tokens.hasMoreTokens())
    {
      String operation = tokens.nextToken();
      amount           = Integer.parseInt(tokens.nextToken());
      field            = tokens.nextToken();
      calField = getField(field);

      if(field.equalsIgnoreCase("weeks") || field.equalsIgnoreCase("week"))
        amount = amount*7;

      if (operation.equals("-"))
        amount = amount*-1;

      cal.add(calField, amount);

      // CALCDT-5: If we fell back, but should have went forward, let's do that now
      if(mustRollForward(calOrig, cal, calField))
      {
        cal.add(Calendar.DAY_OF_MONTH, 1);
      }
    }

    // Drop the time as we don't care about that for Vaccines.
    // Only clinical days
    return dropTime(cal);

  }

  private static int getField(String field) {
    if(field.equalsIgnoreCase("years") || field.equalsIgnoreCase("year"))
      return Calendar.YEAR;
    else if(field.equalsIgnoreCase("months") || field.equalsIgnoreCase("month"))
      return Calendar.MONTH;

    return Calendar.DAY_OF_MONTH;
  }

  public static Date dropTime(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return dropTime(cal);
  }
  public static Date dropTime(Calendar cal) {
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);

    return(cal.getTime());
  }

    private static boolean mustRollForward(Calendar calOrig, Calendar cal, int calField) {
        return ((calField == Calendar.YEAR || calField == Calendar.MONTH ) &&
        calOrig.getActualMaximum(Calendar.DAY_OF_MONTH) > cal.getActualMaximum(Calendar.DAY_OF_MONTH) &&
        cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH) &&
        calOrig.get(Calendar.DAY_OF_MONTH) > cal.get(Calendar.DAY_OF_MONTH));
    }
}
