<template>
  <h1>명예의 전당</h1>
  <div
    class="basebox"
    style="margin: auto; display: flex; width: 1200px; height: 70%"
  >
    <row style="margin: 50px">
      <div class="basebox" style="height: 40px"></div>
      <div
        class="basebox award"
        style="
          height: 200px;
          width: 300px;
          margin: auto;
          background-color: skyblue;
        "
      ></div>
      <div class="basebox" style="height: 60px">
        <img class="medal" src="../../assets/images/second.png" />
      </div>
    </row>
    <row style="margin: 50px">
      <div
        class="basebox award"
        style="
          height: 200px;
          width: 300px;
          margin: auto;
          background-color: skyblue;
        "
      ></div>
      <div class="basebox" style="height: 90px">
        <img class="medal" src="../../assets/images/first.png" />
      </div>
    </row>
    <row style="margin: 50px">
      <div class="basebox" style="height: 80px"></div>
      <div
        class="basebox award"
        style="
          height: 200px;
          width: 300px;
          margin: auto;
          background-color: skyblue;
        "
      ></div>
      <div class="basebox" style="height: 50px">
        <img class="medal" src="../../assets/images/third.png" />
      </div>
    </row>
  </div>
  <hr />
  <div style="display: flex">
    <div class="people_rank">
      <h3>우승횟수 랭킹</h3>
      <div style="width: 90%; margin: auto; display: flex">
        <div class="basebox1">
          <div class="people_rank1" style="height: 20px"></div>
          <h5>asdf</h5>
          <div class="people_rank1" style="height: 100px">
            <img class="ex" src="../../assets/images/user_ex.png" />
          </div>
          <div
            class="people_rank1"
            style="height: 90px; border-top: 5px solid black"
          >
            <img class="mini_medal" src="../../assets/images/second.png" />
          </div>
        </div>
        <div class="basebox1">
          <h5>asdf</h5>
          <div class="people_rank1" style="height: 100px">
            <img class="ex" src="../../assets/images/user_ex.png" />
          </div>
          <div
            class="people_rank1"
            style="height: 100px; border-top: 5px solid black"
          >
            <img class="mini_medal" src="../../assets/images/first.png" />
          </div>
        </div>
        <div class="basebox1">
          <div class="people_rank1" style="height: 20px"></div>
          <h5>asdf</h5>
          <div class="people_rank1" style="height: 100px">
            <img class="ex" src="../../assets/images/user_ex.png" />
          </div>
          <div
            class="people_rank1"
            style="height: 40px; border-top: 5px solid black"
          >
            <img class="mini_medal" src="../../assets/images/third.png" />
          </div>
        </div>
      </div>
    </div>
    <div class="people_rank" style="border-left: 1px solid lightgrey">
      <h3>연승횟수 랭킹</h3>
      <div style="width: 90%; margin: auto; display: flex">
        <div class="basebox1">
          <div class="people_rank1" style="height: 30px"></div>
          <h5>asdf</h5>
          <div class="people_rank1" style="height: 100px">
            <img class="ex" src="../../assets/images/user_ex.png" />
          </div>
          <div
            class="people_rank1"
            style="height: 90px; border-top: 5px solid black"
          >
            <img class="mini_medal" src="../../assets/images/second.png" />
          </div>
        </div>
        <div class="basebox1">
          <h5>asdf</h5>
          <div class="people_rank1" style="height: 100px">
            <img class="ex" src="../../assets/images/user_ex.png" />
          </div>
          <div
            class="people_rank1"
            style="height: 100px; border-top: 5px solid black"
          >
            <img class="mini_medal" src="../../assets/images/first.png" />
          </div>
        </div>
        <div class="basebox1">
          <div class="people_rank1" style="height: 20px"></div>
          <h5>asdf</h5>
          <div class="people_rank1" style="height: 100px">
            <img class="ex" src="../../assets/images/user_ex.png" />
          </div>
          <div
            class="people_rank1"
            style="height: 40px; border-top: 5px solid black"
          >
            <img class="mini_medal" src="../../assets/images/third.png" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RankBox",
};
</script>

<style>
.mini_medal {
  width: 50px;
}
.ex {
  height: 70px;
}
.people_rank1 {
  width: 100%;
  padding: 0;
}
.basebox1 {
  margin: auto;
  padding: 0;
  width: 30%;
}
.basebox {
  margin: 0;
  padding: 0;
  width: 300px;
}
.award {
  border: 5px solid black;
  margin: 2px;
}
.medal {
  width: 80px;
}
.people_rank {
  width: 50%;
  height: 400px;
  margin: 0;
  padding: 0;
}
</style>
