# warehousing
###CSC207 Project: Warehouse system

This is a system software designed for an automotive factory that sells fascias of minivan bumpers. The system basically processes received orders, tracks order status, gives computer support for worker and keeps track of the stock. Each worker in warehouse would hold a barcode reader to connect with the system and get constructions from system.

- Selection and loading of fascia for bumpers

- System:

  system track status of orders, provide computer support for warehousing workers, keep track of inventory level in warehouse.

  - translate orders  into pair of fascia, (front, back) in correct order
  - resupply from reserve room when get replenishing request
  - generating picking request with unique request ID while ther is 4 orders enter system [color, model enter -> look up SKU]
  - check if 5 left on rank of fascia -> replensishing request[get 25]

- order -> single minvan, include color and model
  [translate into pair of fascia, (front, back)] in correct order

- Picker: 
  - hold barcode reader given zone, aisle, rank location and level for next fascia, scan SKU of the fascia picked
  - ask system for next picking reques when ready
  - 4 orders 8 fascia 2 pallet per time
  - after 8 picks, barcode reader give sequencing zone as location

- Pallet: 4 fasia per pallet

- Sequencer:
  - get 8 fascia, 2 pallet, 1 for front, 1 for back
  - after sequence, scan barcode in order: 4 front -> 4 back
  - if incorrect fascia -> generate new picking request

- Loader:
  - look at request id, scan SKU to ensure correct order
  - recorded loaded picked request
  - wait for correct order of reuqest to be loaded

- replenisher: record SKU of fascia repplied

- Barcode reader:
  - [picker] given zone [2: A, B], aisle [2: 0, 1], rack[3: 0, 1, 2] location and level [0, 1, 2, 4] for next fascia
  - scan SKU

- Truck: 
  - 40 orders[80 bumpers], 10 high [request i, request i+1]
  - leave only when full

- Reserve room: store new fascias [never run out]

  - Supply: eacht time 40 same fascias[model, color, front or back]

- FAX machine: one order per FAX, orders wait till there is 4 orders then enter system

- Boundary:
  - Barcode reader is not connected
  - Place, where order enter: FAX machine
  - traversial optimization

- check design pattern:

  - factory design pattern
  - observer design pattern

- Logging:
  - events: pick, sequence
  - Msg from system to barcode reader
  - Msg from barcode reader to system
