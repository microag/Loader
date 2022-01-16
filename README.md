# Excel File Loader
>This library just load simple flat Excel files with error handling.
>
>Just support Excel 2003 files
##Where can I get?
```xml
<dependency>
    <groupId>org.microag</groupId>
    <artifactId>microag-loader</artifactId>
    <version>0.1</version>
</dependency>
```
##Sample Usage
```java
ExcelLoader loader = new ExcelLoader("col1", "col2");
try {
    loader.loadFile(new File("Sample.xls"));
    System.out.println("Row\tColumn 1\t\tColumn 2");
    ExcelValidationResult result = loader.parseFile(new RecordFinder() {
        @Override
        public void read(Record record) {
            if (record.get("col1").equals("error"))
                throw new RuntimeException("This row contain Error!");
            System.out.println(record.getIndex() + "\t\t" + record.get("col1") + "\t\t" + record.get("col2"));
        }
    });
    for (ExcelError err : result.getErrors()) {
        System.out.println(err.getLog());
    }

} catch (FileException e) {
    e.printStackTrace();
}
```
