export class BaseDataSource<T> implements IDataSource {
  private listeners: DataChangeListener[] = [];
  private originDataArray: T[] = [];

  totalCount(): number {
    return this.originDataArray.length
  }

  getData(index: number): T {
    return this.originDataArray[index]
  }

  registerDataChangeListener(listener: DataChangeListener): void {
    if (this.listeners.indexOf(listener) < 0) {
      this.listeners.push(listener);
    }
  }

  unregisterDataChangeListener(listener: DataChangeListener): void {
    const pos = this.listeners.indexOf(listener);
    if (pos >= 0) {
      this.listeners.splice(pos, 1);
    }
  }

  notifyDataReload(): void {
    this.listeners.forEach(listener => {
      listener.onDataReloaded();
    });
  }

  notifyDataAdd(index: number): void {
    this.listeners.forEach(listener => {
      listener.onDataAdd(index);
    });
  }

  public newDataList(data: T[]): void {
    let len = this.originDataArray.length;
    this.originDataArray = this.originDataArray.concat(data);
    this.notifyDataAdd(len);
  }

  public addDataList(data: T[]) {
    let len = this.originDataArray.length;
    this.originDataArray.push(...data)
    this.notifyDataAdd(len);
  }

  public clearData(): void {
    this.originDataArray = [];
    this.notifyDataReload();
  }
}