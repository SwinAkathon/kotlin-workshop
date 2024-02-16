Kotlin Workshop
==================
Github: https://github.com/SwinAkathon/kotlin-workshop

# Kotlin Frontend Development with Jetpack Compose

## References
1. [Jetpack Compose for Android Developers](https://developer.android.com/courses/jetpack-compose/course)

## Basics
### [Compose essentials](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-1): Topics 1-7 (Topic 7: exclude ViewModel)

#### Thinking in Compose
#### Composable Functions
#### First Compose App
#### Compose UI toolkit
#### Implement a real-world design
#### Getting started with state

### [Layouts, theming and animation](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-2)
#### Fundamentals of Compose layouts and modifiers
#### Lazy layouts
#### Material design system
#### Theme your app with Material Design 3
#### Styling text
#### Animating elements
#### Custom layouts and graphics
#### (Optional) Constraints and modifier order


- [Compose Layouts/Fundamentals of Compose Layouts and Modifiers](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-2)

### Examples
1. [Calculator Basics](https://github.com/SwinAkathon/kotlin-workshop/tree/basic)
   - (statement management basic) automatically update CalculatorScreen's dimension and layout on device rotation
2. CustomerStreamApp Basics
2. BasicsCodelab
3. BasicLayoutsCodelab
4. BasicStateCodelab
5. (Optional) MigrationCodelab
5. ThemingCodeLab
6. (Optional) AnimationCodeLab

## Intermediate (Medium)

### Outline
[Architecture and State](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-3): Topics 2, 8

#### Topic 2: Architecting your Compose UI
#### Topic 8: State holders and state production

#### [State in Jetpack Compose/State in ViewModel](https://developer.android.com/codelabs/jetpack-compose-state)

### Examples
1. (+) [Calculator (Intermediate)](calculator)
   - State management in-sync with configuration updates.
     Manages calculator state using ViewModel to retain state on rotation and app switch
2. [Codelab-android-paging](https://github.com/android/codelab-android-paging)
3. [Jetchat](https://github.com/android/compose-samples/tree/main/Jetchat)
  
## Advanced
### Outline
[Architecture and state](https://developer.android.com/courses/pathways/jetpack-compose-for-android-developers-3): Topics 1,3-8

#### Compose phases
#### A Compose state of mind
#### Where to hoist your state
#### Advanced state and side effects
#### Navigation best practices
#### Navigation

### Examples
1. (Topic 5) AdvancedStateAndSideEffectsCodelab
2. (+) (Topic 7) NavigationCodelab
3. Calculator (Advanced)

# Kotlin Frontend with Large Data Stream Processing

## Prerequisites
1. [Advanced coroutines with Kotlin Flow and LiveData](https://developer.android.com/codelabs/advanced-kotlin-coroutines#0)
   2. Example: advanced-coroutines-codelab
2. Branch `medium` or above with Jetpack compose

## References
### Pagination
- [Library overview](https://developer.android.com/jetpack/androidx/releases/paging)
- [Jetpack Paging library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Android Paging Basics codelab](https://developer.android.com/codelabs/android-paging-basics#0)
- [Android Paging Advanced codelab](https://developer.android.com/codelabs/android-paging#0)

## Example: CustomerStreamApp (intermediate-advanced)
[CustomerStreamApp (Intermediate)](https://github.com/SwinAkathon/kotlin-workshop/tree/medium)

## Using ViewModel to manage configuration-aware state

### CustomerAct
```
  private lateinit var custViewModel: CustomerViewModel

  override fun onCreate(savedInstanceState: Bundle?)
    // initialise ViewModel (once)
    if (!::custViewModel.isInitialized) {
      custViewModel =
        ViewModelProvider(this).get(CustomerViewModel::class.java)
      Log.d(LTAG, "view model initialised")
    }

    custViewModel.assetMan = context.assets
    ...
    CustomerStreamScreen(context, custViewModel)
    
    @Composable
    fun CustomerStreamScreen(context: Context, custViewModel: CustomerViewModel)
      val pagingItems = custViewModel.customerFlow.collectAsLazyPagingItems()
    
      LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(pagingItems.itemCount) { index ->
          pagingItems[index]?.let {
            CustomerItem(customer = it, onCheckedChange = { isChecked ->
              // todo: Handle customer selection
              notify(context, it.toString())
            })
          }
        }
      }
```

## Setting up ViewModel for Pagination-aware data read

### CustomerViewModel

`customerFlow`:
- a Kotlin data flow
- is set up for pagination, using `CustomerPagingSource` as the data source

Data source settings:
- `filePath = customers.csv`: relative to the `src/main/assets` folder
- `pageSize = 5`: how many objects to read from the data source each time

```
class CustomerViewModel() : ViewModel() {
  // use Paging to obtain objects
  val pageSize = 5
  private val filePath = "customers.csv"

  lateinit var assetMan: AssetManager

  val customerFlow = Pager(PagingConfig(pageSize = this.pageSize)) {
    CustomerPagingSource(assetMan,filePath)
  }.flow.cachedIn(viewModelScope)
}
```

## Reading data from an external source with pagination

Incrementally read data from a CSV file, one page at a time. Remember the location pointer to read
next page (on user scrolling):

### `CustomerPagingSource`

1. Sets up the `BufferredReader` ready to read
```
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Customer> {
    //...
    val pageNumber = params.key ?: 0
    val pageSize = params.loadSize
    val readAheadLimit = 1024

    Log.d(LTAG, "loading page ${pageNumber} (pageSize: ${pageSize})...")
    // load objects for the current page
    if (!::reader.isInitialized) {
      // Open the CSV file
      val inputStream = assetMan.open(filePath)
      reader = inputStream.bufferedReader()
      // Skip the header row
      reader.readLine()
    }
    //..
  }
```

2. Read up to `pageSize` number of objects
- `read.mark`: mark the reading location to ease next read
```
  // Read lines and parse
  val customers = mutableListOf<Customer>()
  var line: String?
  var readCount = 0
  while ((reader.readLine().also { line = it } != null) && readCount < pageSize) {
    val tokens = line!!.split(",")
    // record format: id,name
    val customer = Customer(tokens[0].toInt(), tokens[1])
    customers.add(customer)
    readCount++
  }

  reader.mark(readAheadLimit)
```

3. Set up previous and next pages, ready for subsequent read
```
  // setup prev, next keys
  val prevKey = if (pageNumber > 0) pageNumber - 1 else null
  val nextKey = if (customers.isNotEmpty()) pageNumber + 1 else null

  Log.d(LTAG, "...read count (${readCount}); previous: ${prevKey}; next: ${nextKey}...")

  return LoadResult.Page(
    data = customers,
    prevKey = prevKey,
    nextKey = nextKey
  )
```

## Handling a large object stream and displays them on a Composable with pagination

## (Optional) LazyColumn with ScrollBar
https://github.com/nanihadesuka/LazyColumnScrollbar