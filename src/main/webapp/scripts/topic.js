displayAllTopics();

function clickedTopicTitle(topicId) {
    const url = window.location.href.split('/').slice(0,-1).join('/')+'/topics/view?topic_id=' + topicId
    window.location.href = url;
}

function displayAllTopics() {
    // const topicPanel = document.querySelector(".topicPanel");

    // topicPanel.appendChild(createTopicHeaderElement(1, "title1", "author1", Date()))
}

function createTopicHeaderElement(topicId, topicTitle, topicAuthor, topicDate) {
    const topicContainer = document.createElement("div");
    topicContainer.className = "topicContainer";

    const topicPresentation = document.createElement("div");
    topicPresentation.className = "topicPresentation";

    const topicDetails = document.createElement("div");
    topicDetails.className = "topicDetails";

    const topicDetailsName = document.createElement("div");
    topicDetailsName.className = "topicDetailsElement";
    topicDetailsName.innerText = "[ " + topicAuthor + " ]";

    const topicDetailsDate = document.createElement("div");
    topicDetailsDate.className = "topicDetailsElement";
    topicDetailsDate.innerText = topicDate;

    topicDetails.appendChild(topicDetailsName);
    topicDetails.appendChild(topicDetailsDate);

    const topicLink = document.createElement("div");

    const topicTitleElement = document.createElement("h3");
    topicTitleElement.className = "topicLink";
    topicTitleElement.onclick = () => { console.log("topic: " + topicId); }
    topicTitleElement.innerText = topicTitle;

    topicLink.appendChild(topicTitleElement);
    topicPresentation.appendChild(topicDetails);
    topicPresentation.appendChild(topicLink);
    topicContainer.appendChild(topicPresentation);

    console.log(topicContainer);
    return topicContainer;
}
